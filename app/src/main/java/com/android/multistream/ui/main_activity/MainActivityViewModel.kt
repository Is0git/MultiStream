package com.android.multistream.ui.main_activity

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.android.multistream.auth.platforms.Platform
import com.android.multistream.di.main_activity.MainActivityScope
import com.android.multistream.network.twitch.models.v5.current_user.CurrentUser
import javax.inject.Inject

@MainActivityScope
class MainActivityViewModel @Inject constructor(val repo: MainActivityRepository) : ViewModel() {

    val statesLiveData = repo.twitchPlatformAuthLiveData

    fun getAndSaveToken(code: Uri?, platformClass: Class<out Platform<*, *, *, *>>) {
        repo.getAndSaveToken(code, platformClass)
    }

    fun isValidated(clazz: Class<out Platform<*, *, *, *>>): Boolean {
        return repo.isValidated(clazz)
    }

    suspend fun validateToken(clazz: Class<out Platform<*, *, *, *>>) {
        repo.validateToken(clazz)
    }

    suspend fun validateAccessTokens() {
        repo.validateAccessTokens()
    }

    fun getToken(clazz: Class<out Platform<*, *, *, *>>): String? {
        return repo.getToken(clazz)
    }

    fun getTwitchUser(): CurrentUser? {
        return repo.getTwitchUser()
    }

    fun saveAccessToken(
        platformClass: Class<out Platform<*, *, *, *>>,
        accessToken: String?,
        refreshToken: String?
    ) {
        repo.saveAccessToken(platformClass, accessToken, refreshToken)
    }

}