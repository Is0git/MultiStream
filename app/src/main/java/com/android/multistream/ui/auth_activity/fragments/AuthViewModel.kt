package com.android.multistream.ui.auth_activity.fragments

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.multistream.auth.platforms.Platform
import com.android.multistream.di.auth_activity.AuthScope
import com.android.multistream.network.twitch.models.v5.current_user.CurrentUser
import kotlinx.coroutines.launch
import javax.inject.Inject

@AuthScope
class AuthViewModel @Inject constructor(var repo: AuthRepository): ViewModel() {

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
        viewModelScope.launch { repo.saveAccessToken(platformClass, accessToken, refreshToken) }
    }
}