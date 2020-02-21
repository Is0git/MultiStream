package com.android.multistream.auth

import com.android.multistream.network.mixer.models.channel.User
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit


/**
 * @param  platformRetrofit retrofit for specific platform, used to create service to  get/validate auth token
 * @param serviceClass class reference to create retrofit service
 */
abstract class Platform<T : Any, S : Any>(
    var platformRetrofit: Retrofit,
    var serviceClass: Class<T>,
    var platformManager: PlatformManager
)  {

    var service: T = platformRetrofit.create(serviceClass)

    var accessTokenJob: Job? = null

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


    abstract suspend fun getAccessTokenBearer(service: T, code: String) : Response<S>

    abstract suspend fun saveAccessToken(response: Response<S>, platformManager: PlatformManager, platform: Platform<*, *>)
}