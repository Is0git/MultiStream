package com.android.multistream.auth.Platforms

import com.android.multistream.auth.Platform
import com.android.multistream.auth.PlatformManager
import com.android.multistream.di.TwitchRetrofitQualifier
import com.android.multistream.network.twitch.TwitchAuthService
import com.android.multistream.network.twitch.models.Token
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitchPlatform @Inject constructor(
    @TwitchRetrofitQualifier val retrofit: Retrofit, platformManager: PlatformManager
) : Platform<TwitchAuthService, Token>(retrofit, TwitchAuthService::class.java, platformManager) {
    override suspend fun getAccessTokenBearer(
        service: TwitchAuthService,
        code: String
    ): Response<out Any> {
        return service.getAuthToken(code)

    }

    override suspend fun saveAccessToken(
        response: Response<Token>,
        platformManager: PlatformManager,
        platform: Platform<*, *>
    ) {
        response.body().also { token ->
            platformManager.sharedPreferencesEditor.also {
                val className = platform.javaClass.simpleName
                it.putString("ACCESS_TOKEN_$className", token?.accessToken)
                it.putString("REFRESH_TOKEN_$className", token?.refreshToken)
                it.apply()
            }
        }
    }
}