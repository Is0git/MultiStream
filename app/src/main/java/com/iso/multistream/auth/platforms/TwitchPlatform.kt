package com.iso.multistream.auth.platforms

import android.app.Application
import com.iso.multistream.auth.platform_manager.PlatformManager
import com.iso.multistream.di.qualifiers.TwitchAuthQualifier
import com.iso.multistream.network.twitch.TwitchAuthService
import com.iso.multistream.network.twitch.models.auth.Token
import com.iso.multistream.network.twitch.models.auth.Validation
import com.iso.multistream.network.twitch.models.v5.current_user.CurrentUser
import com.iso.multistream.utils.ResponseHandler
import kotlinx.coroutines.CancellationException
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitchPlatform @Inject constructor(
    @TwitchAuthQualifier val retrofit: Retrofit, platformManager: PlatformManager,
    val app: Application
) : Platform<TwitchAuthService, Token, Validation, CurrentUser>(
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
        return service.checkValidation("OAuth $accessToken")
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

    override suspend fun getUser(accessToken: String): CurrentUser {
        val result = service.getCurrentUser("OAuth $accessToken")
        val isSuccessful = ResponseHandler.handleResponse(result, null)
        return if (isSuccessful) result.body()!! else throw CancellationException("couldn't get user")
    }
}