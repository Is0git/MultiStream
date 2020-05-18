package com.android.multistream.auth.platform_manager

import android.content.SharedPreferences
import android.util.Log
import com.android.multistream.auth.platforms.Platform
import javax.inject.Inject


class PlatformManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    val sharedPreferencesEditor: SharedPreferences.Editor
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