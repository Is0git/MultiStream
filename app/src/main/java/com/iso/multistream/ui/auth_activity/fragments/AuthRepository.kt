package com.iso.multistream.ui.auth_activity.fragments

import android.app.Application
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.iso.multistream.auth.platform_manager.PlatformManager
import com.iso.multistream.auth.platforms.Platform
import com.iso.multistream.auth.platforms.TwitchPlatform
import com.iso.multistream.di.auth_activity.AuthScope
import com.iso.multistream.di.qualifiers.SettingsPreferencesQualifier
import com.iso.multistream.network.mixer.MixerService
import com.iso.multistream.network.twitch.TwitchService
import com.iso.multistream.network.twitch.models.auth.Token
import com.iso.multistream.network.twitch.models.v5.current_user.CurrentUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@AuthScope
class AuthRepository @Inject constructor(
    var application: Application,
    var twitchService: TwitchService,
    var mixerService: MixerService,
    val platformManager: PlatformManager,
    @SettingsPreferencesQualifier
    var settingsPreferences: SharedPreferences.Editor
) {
    val twitchPlatformAuthLiveData =
        platformManager.getPlatform(TwitchPlatform::class.java).statesLiveData

    fun getAndSaveToken(code: Uri?, platformClass: Class<out Platform<*, *, *, *>>) {
        platformManager.getPlatform(TwitchPlatform::class.java)
            .saveAccessTokenBearer(code, Token::class.java)
    }

    fun isValidated(clazz: Class<out Platform<*, *, *, *>>): Boolean {
        return platformManager.getPlatform(clazz).isValidated
    }

    suspend fun validateToken(clazz: Class<out Platform<*, *, *, *>>) {
        platformManager.getPlatform(clazz).validateAccessToken()
    }

    suspend fun validateAccessTokens() {
        delay(3000)
        platformManager.platforms.forEach {
            supervisorScope {
                var isValidated = false
                isValidated = try {
                    it.value.validateAccessToken()
                    true
                } catch (ex: Exception) {
                    Log.e("AUTH", ex.message)
                    false
                } finally {
                    settingsPreferences.putBoolean("${it.value.platformName}_sync", isValidated)
                }
            }
        }
    }

    fun getToken(clazz: Class<out Platform<*, *, *, *>>): String? {
        return platformManager.getAccessToken(clazz)
    }

    fun getTwitchUser(): CurrentUser? {
        return platformManager.getPlatform(TwitchPlatform::class.java).currentUser
    }

    fun saveAccessToken(
        platformClass: Class<out Platform<*, *, *, *>>,
        accessToken: String?,
        refreshToken: String?
    ) {
        platformManager.saveAccessTokenInPreference(platformClass, accessToken, refreshToken)
    }

}