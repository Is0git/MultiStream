package com.android.multistream.auth.Platforms

import android.app.Application
import com.android.multistream.auth.Platform
import com.android.multistream.auth.PlatformManager
import com.android.multistream.di.qualifiers.TwitchQualifier
import com.android.multistream.network.twitch.TwitchAuthService
import com.android.multistream.network.twitch.models.Token
import com.android.multistream.network.twitch.models.Validation
import kotlinx.coroutines.CancellationException
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitchPlatform @Inject constructor(
    @TwitchQualifier val retrofit: Retrofit, platformManager: PlatformManager,
    val app: Application
) : Platform<TwitchAuthService, Token, Validation>(
    retrofit,
    TwitchAuthService::class.java,
    platformManager
) {
    override suspend fun getAccessTokenBearer(
        service: TwitchAuthService,
        code: String
    ): Response<Token> {
        return service.getAuthToken(code)

    }

    override suspend fun getTokenValidationResponse(
        service: TwitchAuthService,
        accessToken: String
    ): Response<Validation> {
        return service.checkValidation(accessToken)
    }

    override fun provideAuthTokenPair(response: Response<Token>): Pair<String?, String?> {
        response.body().also { token ->
            return Pair(token?.access_token, token?.refresh_token)
        }
    }

    override fun getNewToken(service: TwitchAuthService, refreshToken: String): Response<Token>? {
        return service.refreshToken(refreshToken = refreshToken).let {
            val token = it.execute()
            token.run {
                when {
                    isSuccessful -> this
                    else -> throw CancellationException("null token response")
                }
            }
        }
    }


}