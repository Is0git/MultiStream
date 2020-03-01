package com.android.multistream.auth

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.multistream.utils.uriQuery
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit


/**
 * @param  platformRetrofit retrofit for specific platform, used to create service to  get/validate auth token
 * @param serviceClass class reference to create retrofit service
 *
 * @property T retrofit service class
 * @property S service response class
 * @property U service validation class
 */
abstract class Platform<T : Any, S : Any, U : Any>(
    var platformRetrofit: Retrofit,
    var serviceClass: Class<T>,
    var platformManager: PlatformManager
) {

    sealed class AuthState{
        object Completed : AuthState()

        data class Failed(val throwable: Throwable) : AuthState()
    }

    var service: T = platformRetrofit.create(serviceClass)

    var accessTokenJob: Job? = null

    var validationJob: Job? = null

    var isValidated: Boolean = false

    // listener for auth states during authentication
    var authListener: AuthListener? = null

    val statesLiveData by lazy { MutableLiveData<AuthState>() }

    /**
     * @param code code for bearer auth
     * @param S type of service Response object
     */

    init {
        accessTokenJob?.invokeOnCompletion { it?.also { statesLiveData.value = AuthState.Failed(it) } }
    }

    fun saveAccessTokenBearer(uriData: Uri?, responseClass: Class<S>) {
        accessTokenJob = CoroutineScope(Dispatchers.IO).launch {
            val code = (if (uriData == null) {
                throw CancellationException("response uri can't be null") as Throwable
            } else getBearerCode(uriData.toString()))
                ?: throw CancellationException("Code was not found")

            val responseResult = getAccessTokenBearer(service, code)

            if (responseResult.body() == null) throw CancellationException("access token result is null")

            if (!responseResult.body()?.javaClass?.isAssignableFrom(responseClass)!!) throw CancellationException(
                "function's token response does not match with service response"
            )
            val pair = provideAuthTokenPair(responseResult)
            saveAccessTokenInPreference(pair, platformManager, this@Platform)

            withContext(Dispatchers.Main) {statesLiveData.value = AuthState.Completed}
        }
    }

    private fun saveAccessTokenInPreference(
        authPair: Pair<String?, String?>,
        platformManager: PlatformManager,
        platform: Platform<*, *, *>
    ) {

        if (authPair.first != null && authPair.second != null) {
            platformManager.sharedPreferencesEditor.also {
                val className = platform.javaClass.simpleName
                it.putString("ACCESS_TOKEN_$className", authPair.first)
                it.putString("REFRESH_TOKEN_$className", authPair.second)
                it.apply()
            }
        }
    }

    /**
     * Validates access token
     */
    fun validateAccessToken() {
        validationJob = CoroutineScope(Dispatchers.IO).launch {
            val accessToken = platformManager.getAccessToken(this@Platform::class.java)
                ?: throw CancellationException("You need to get access token before validation")
            val validationResponse = getTokenValidationResponse(service, accessToken)

            validationResponse.apply {
                isValidated = when {
                    body() == null -> throw  CancellationException("response is null")
                    isSuccessful -> true
                    else -> false
                }
            }
        }
    }

    fun refreshToken(): String? {
        val refreshToken = platformManager.getRefreshToken(this::class.java) ?: return "null"

        val token = getNewToken(service, refreshToken)?.let {
            val pair = provideAuthTokenPair(it)
            saveAccessTokenInPreference(provideAuthTokenPair(it), platformManager, this)
            Log.d("TESTPREF", "FIRST: ${pair.first} SECOND: ${pair.second}")
            return pair.first
        }
        return null
    }

    private fun getBearerCode(uriData: String) : String? =
        uriQuery(uriData)


    /**
     * run on current thread(because used in okHttpClient authenticator)
     */
    abstract fun getNewToken(service: T, refreshToken: String): Response<S>?

    abstract fun provideAuthTokenPair(response: Response<S>): Pair<String?, String?>

    abstract suspend fun getTokenValidationResponse(service: T, accessToken: String): Response<U>

    abstract suspend fun getAccessTokenBearer(service: T, code: String): Response<S>


}