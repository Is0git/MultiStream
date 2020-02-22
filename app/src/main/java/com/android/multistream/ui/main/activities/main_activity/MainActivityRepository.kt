package com.android.multistream.ui.main.activities.main_activity

import android.net.Uri
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import com.android.multistream.auth.PlatformManager
import com.android.multistream.auth.Platforms.TwitchPlatform
import com.android.multistream.network.twitch.models.Token
import javax.inject.Inject

@MainActivityScope
class MainActivityRepository @Inject constructor(val platformManager: PlatformManager) {

    val twitchPlatformAuthLiveData = platformManager.getPlatform(TwitchPlatform::class.java).statesLiveData

    fun getAndSaveToken(code: Uri?) {
        platformManager.getPlatform(TwitchPlatform::class.java).saveAccessTokenBearer(code, Token::class.java)
    }


}