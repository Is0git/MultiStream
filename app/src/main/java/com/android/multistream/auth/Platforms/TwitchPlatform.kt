package com.android.multistream.auth.Platforms

import android.app.Application
import android.widget.Toast
import androidx.core.util.toAndroidPair
import com.android.multistream.auth.Platform
import com.android.multistream.auth.PlatformManager
import com.android.multistream.di.TwitchRetrofitQualifier
import com.android.multistream.network.twitch.TwitchAuthService
import com.android.multistream.network.twitch.models.Token
import com.android.multistream.network.twitch.models.Validation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitchPlatform @Inject constructor(
    @TwitchRetrofitQualifier val retrofit: Retrofit, platformManager: PlatformManager,
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
            return  Pair(token?.access_token, token?.refresh_token)
        }
    }


}