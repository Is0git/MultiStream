package com.android.multistream.auth

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

    var service: T = platformRetrofit.create(serviceClass)

    var accessTokenJob: Job? = null

    var validationJob: Job? = null

    var isValidated: Boolean = false

    /**
     * @param code code for bearer auth
     * @param S type of service Response object
     */
    fun saveAccessTokenBearer(code: String?, responseClass: Class<S>) {
        accessTokenJob = CoroutineScope(Dispatchers.IO).launch {
            if (code == null) {
                throw CancellationException("code can't be null")
            }
            val responseResult = getAccessTokenBearer(service, code)

            if (responseResult.body() == null) throw CancellationException("access token result is null")

            if (!responseResult.body()?.javaClass?.isAssignableFrom(responseClass)!!) throw CancellationException(
                "function's token response does not match with service response"
            )

            saveAccessToken(responseResult, platformManager, this@Platform)
        }
    }

    /**
     * Validates access token
     */
   fun validateAccessToken() {
       validationJob = CoroutineScope(Dispatchers.IO).launch {
            val accessToken = platformManager.getAccessToken(this@Platform::class.java)
                if (accessToken == null) throw CancellationException("You need to get access token before validation")
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

    abstract suspend fun getAccessTokenBearer(service: T, code: String): Response<S>

    abstract suspend fun saveAccessToken(
        response: Response<S>,
        platformManager: PlatformManager,
        platform: Platform<*, *, *>
    )

    abstract suspend fun getTokenValidationResponse(service: T, accessToken: String): Response<U>


}