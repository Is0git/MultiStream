package com.android.multistream.auth

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton


class PlatformManager @Inject constructor(
    val sharedPreferences: SharedPreferences,
    val sharedPreferencesEditor: SharedPreferences.Editor
) {


    val platforms: MutableMap<String, Platform<*, *, *>> =  mutableMapOf()


    fun getAccessToken(platformClass: Class<out Platform<*, *, *>>): String? {
        val res = sharedPreferences.getString("ACCESS_TOKEN_${platformClass.simpleName}", null)

        Log.d("TESTPREF", "$res size: ${sharedPreferences.all}" )
        return res
    }

    fun getAccessTokenByPlatform(platform: Platform<*, *, *>) : String? {
       return sharedPreferences.getString("ACCESS_TOKEN_${platform.javaClass.simpleName}", null)
    }

    fun getRefreshToken(platformClass: Class<out Platform<*, *, *>>): String? {
        return sharedPreferences.getString("REFRESH_TOKEN_${platformClass.simpleName}", null)
    }

    fun getRefreshTokenByPlatform(platform: Platform<*, *, *>) : String? {
        return sharedPreferences.getString("REFRESH_TOKEN_${platform.javaClass.simpleName}", null)
    }

    fun <T : Platform<*, *, *>> addPlatform(platform: T) {
        platforms[platform.javaClass.simpleName] = platform
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Platform<*, *, *>> getPlatform(platformClass: Class<T>) : T = run {
      val classResult = platforms[platformClass.simpleName]
        if (classResult != null) classResult as T else throw IllegalArgumentException("this type platform hasn't been found")
    }

}