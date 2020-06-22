package com.iso.multistream.auth.platform_manager

import android.content.SharedPreferences
import android.util.Log
import com.iso.multistream.auth.platforms.Platform
import com.iso.multistream.di.qualifiers.AuthPreferencesQualifier
import javax.inject.Inject

const val AUTHTAG = "AUTHTAG"

class PlatformManager @Inject constructor(
    @AuthPreferencesQualifier val sharedPreferences: SharedPreferences,
    @AuthPreferencesQualifier val sharedPreferencesEditor: SharedPreferences.Editor
) {

    val platforms: MutableMap<String, Platform<*, *, *, *>> = mutableMapOf()


    fun getAccessToken(platformClass: Class<out Platform<*, *, *, *>>): String? {
        val res = sharedPreferences.getString("ACCESS_TOKEN_${platformClass.simpleName}", null)
        Log.d("TESTPREF", "$res size: ${sharedPreferences.all}")
        return res
    }

    fun getAccessTokenByPlatform(platform: Platform<*, *, *, *>): String? {
        return sharedPreferences.getString("ACCESS_TOKEN_${platform.javaClass.simpleName}", null)
    }

    fun getRefreshToken(platformClass: Class<out Platform<*, *, *, *>>): String? {
        return sharedPreferences.getString("REFRESH_TOKEN_${platformClass.simpleName}", null)
    }

    fun getRefreshTokenByPlatform(platform: Platform<*, *, *, *>): String? {
        return sharedPreferences.getString("REFRESH_TOKEN_${platform.javaClass.simpleName}", null)
    }

    fun <T : Platform<*, *, *, *>> addPlatform(platform: T) {
        platforms[platform.javaClass.simpleName] = platform
    }

    /**
     *@return return true if invalidation was successful or platform is already in invalidated state, otherwise false
     */
    fun invalidateToken(platformClass: Class<out Platform<*, *, *, *>>): Boolean {
        val className = platformClass.javaClass.simpleName
        val platform = getPlatform(platformClass)
        if (!platform.isValidated) {
            Log.i(AUTHTAG, "$className is not validated")
            return true
        }
        sharedPreferences
        sharedPreferences.edit().clear().commit()
        val token = sharedPreferences.getString("ACCESS_TOKEN_${platformClass.simpleName}", null)
        val refreshToken = sharedPreferences.getString("REFRESH_TOKEN${platformClass.simpleName}", null)
        token
        refreshToken
        getPlatform(platformClass).platformManager.getAccessToken(platformClass)
        platform.invalidateToken()
        return true
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Platform<*, *, *, *>> getPlatform(platformClass: Class<T>): T = run {
        val classResult = platforms[platformClass.simpleName]
        if (classResult != null) classResult as T else throw IllegalArgumentException("this type platform hasn't been found")
    }

    fun saveAccessTokenInPreference(
        platformClass: Class<out Platform<*, *, *, *>>,
        accessToken: String?,
        refreshToken: String?
    ) {
        if (!accessToken.isNullOrBlank()) {
            platforms[platformClass.simpleName]?.saveAccessTokenInPreference(
                Pair(
                    accessToken,
                    refreshToken
                ), this
            )
        }
    }
}