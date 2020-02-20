package com.android.multistream.auth

import com.android.multistream.network.mixer.models.channel.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit


/**
 * @param  platformRetrofit retrofit for specific platform, used to create service to  get/validate auth token
 * @param serviceClass class reference to create retrofit service
 */
abstract class Platform<T : Any, S>(
    var platformRetrofit: Retrofit,
    var serviceClass: Class<out T>,
    var platformManager: PlatformManager
)  {

    var service: T = platformRetrofit.create(serviceClass)

    var accessTokenJob: Job? = null

    init {

    }

    /**
     * @param code code for bearer auth
     * @param S type of service Response object
     */
    @Suppress("UNCHECKED_CAST")
    fun saveAccessTokenBearer(code: String?, responseClass: Class<S>) {
        accessTokenJob = CoroutineScope(Dispatchers.IO).launch {
            code?.let { throw NullPointerException("code can't be null") }
            val responseResult = getAccessTokenBearer(service, code!!)

            if (responseResult.body() == null) throw NullPointerException("access token result is null")

            if (!responseResult.body()?.javaClass?.isAssignableFrom(responseClass)!!) throw ClassNotFoundException(
                "function's token response does not match with service response"
            )

            saveAccessToken(responseResult as Response<S>, platformManager, this@Platform)
        }
    }


    abstract suspend fun getAccessTokenBearer(service: T, code: String) : Response<out Any>

    abstract suspend fun saveAccessToken(response: Response<S>, platformManager: PlatformManager, platform: Platform<*, *>)
}