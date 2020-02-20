package com.android.multistream.auth

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

const val TWITCH_TOKEN = "TWITCH_TOKEN"
const val YOUTUBE_TOKEN = "YOUTUBE_TOKEN"
const val MIXER_TOKEN = "MIXER_TOKEN"

@Singleton
class PlatformManager @Inject constructor(
    val sharedPreferences: SharedPreferences,
    val sharedPreferencesEditor: SharedPreferences.Editor
) {


    val platforms: MutableMap<String, Platform<*, *>> by lazy { mutableMapOf<String, Platform<*, *>>() }


    fun getAccessToken(platformClass: Class<out Platform<*, *>>): String? {
        return sharedPreferences.getString("ACCESS_TOKEN_${platformClass.simpleName}", null)
    }

    fun getAccessTokenByPlatform(platform: Platform<*, *>) : String? {
       return sharedPreferences.getString("ACCESS_TOKEN_${platform.javaClass.simpleName}", null)
    }

    fun getRefreshToken(platformClass: Class<out Platform<*, *>>): String? {
        return sharedPreferences.getString("REFRESH_TOKEN_${platformClass.simpleName}", null)
    }

    fun getRefreshTokenByPlatform(platform: Platform<*, *>) : String? {
        return sharedPreferences.getString("REFRESH_TOKEN_${platform.javaClass.simpleName}", null)
    }

    fun addPlatform(platform: Platform<*, *>) {
        platforms[platform.javaClass.canonicalName!!] = platform
    }

    fun getPlatform(platformClass: Class<out Platform<*, *>>) = run {
        platforms[platformClass.javaClass.canonicalName]
    }
}