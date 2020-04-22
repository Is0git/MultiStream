package com.android.multistream.ui.main.activities.main_activity

import android.net.Uri
import android.util.Log
import com.android.multistream.auth.Platform
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import com.android.multistream.auth.PlatformManager
import com.android.multistream.auth.Platforms.TwitchPlatform
import com.android.multistream.network.twitch.models.auth.Token
import kotlinx.coroutines.supervisorScope
import java.lang.Exception
import javax.inject.Inject
import kotlin.reflect.KClass

@MainActivityScope
class MainActivityRepository @Inject constructor(val platformManager: PlatformManager) {

    val twitchPlatformAuthLiveData = platformManager.getPlatform(TwitchPlatform::class.java).statesLiveData

    fun getAndSaveToken(code: Uri?) {
        platformManager.getPlatform(TwitchPlatform::class.java).saveAccessTokenBearer(code, Token::class.java)
    }

    fun isValidated(clazz: Class<out Platform<*, *, *>>) : Boolean {
       return platformManager.getPlatform(clazz).isValidated
    }

   suspend fun validateToken(clazz: Class<out Platform<*, *, *>>) {
        platformManager.getPlatform(clazz).validateAccessToken()
    }

  suspend  fun validateAccessTokens() {
        platformManager.platforms.forEach { supervisorScope {
            try {
                it.value.validateAccessToken()
            } catch (ex: Exception) {
                Log.e("AUTH", ex.message)
            }
        } }
    }

}