package com.android.multistream.ui.main.activities.main_activity

import com.android.multistream.di.MainActivity.MainActivityScope
import com.android.multistream.utils.AuthHandler
import javax.inject.Inject

@MainActivityScope
class MainActivityRepository @Inject constructor(val authHandler: AuthHandler) {

   suspend fun authorize(type: String, token: String) {
        authHandler.authorize(type, token)
    }

    fun getToken(type: String) = authHandler.getToken(type)
}