package com.android.multistream.auth

import android.content.SharedPreferences
import retrofit2.Response

/**
 * Service for handling auth in Platform class
 */
interface AuthService<T, S> {

    fun getAccessTokenBearer(service: T, code: String) : Response<out Any>

    fun saveAccessToken(response: Response<S>, platformManager: PlatformManager)
}