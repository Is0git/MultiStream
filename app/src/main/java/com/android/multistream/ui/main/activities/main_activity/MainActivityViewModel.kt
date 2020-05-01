package com.android.multistream.ui.main.activities.main_activity

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.android.multistream.auth.Platform
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import com.android.multistream.network.twitch.models.v5.user.CurrentUser
import javax.inject.Inject

@MainActivityScope
class MainActivityViewModel @Inject constructor(val repo: MainActivityRepository) : ViewModel() {

    val statesLiveData = repo.twitchPlatformAuthLiveData

    fun getAndSaveToken(code: Uri?) {
        repo.getAndSaveToken(code)
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

}