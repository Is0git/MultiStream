package com.iso.multistream.auth.platforms

import com.iso.multistream.auth.platform_manager.PlatformManager
import com.iso.multistream.di.qualifiers.MixerQualifier
import com.iso.multistream.network.mixer.MixerService
import com.iso.multistream.network.twitch.models.auth.Token
import com.iso.multistream.network.twitch.models.auth.Validation
import com.iso.multistream.network.twitch.models.v5.current_user.CurrentUser
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MixerPlatform @Inject constructor(@MixerQualifier retrofit: Retrofit, platformManager: PlatformManager) :
    Platform<MixerService, Token, Validation, CurrentUser>(
        retrofit,
        MixerService::class.java,
        platformManager,
        "Mixer"
    ) {

    override fun getNewToken(service: MixerService, refreshToken: String): Response<Token>? {
        return null
    }

    override fun provideAuthTokenPair(response: Response<Token>): Pair<String?, String?> {
        return Pair(response.body()?.access_token, response.body()?.refresh_token)
    }

    override suspend fun getUser(accessToken: String): CurrentUser {
        return CurrentUser()
    }

    override suspend fun getTokenValidationResponse(
        service: MixerService,
        accessToken: String
    ): Response<Validation> {
        return service.getValidation()
    }

    override suspend fun getAccessTokenBearer(
        service: MixerService,
        code: String
    ): Response<Token> {
        return service.getToken()
    }
}