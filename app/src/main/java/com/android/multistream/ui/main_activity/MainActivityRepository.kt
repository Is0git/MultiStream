package com.android.multistream.ui.main_activity

import android.net.Uri
import android.util.Log
import com.android.multistream.auth.platforms.Platform
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import com.android.multistream.network.twitch.models.auth.Token
import com.android.multistream.network.twitch.models.v5.current_user.CurrentUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@MainActivityScope
class MainActivityRepository @Inject constructor(val platformManager: PlatformManager) {

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
        delay(2500)
        platformManager.platforms.forEach {
            supervisorScope {
                try {
                    it.value.validateAccessToken()
                } catch (ex: Exception) {
                    Log.e("AUTH", ex.message)
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