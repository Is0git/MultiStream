package com.android.multistream.util

import android.content.SharedPreferences
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

const val TWITCH_TOKEN = "TWITCH_TOKEN"
const val YOUTUBE_TOKEN = "YOUTUBE_TOKEN"
const val MIXER_TOKEN = "MIXER_TOKEN"

@Singleton
class AuthHandler @Inject constructor(val retrofit: Retrofit, val sharedPreferences: SharedPreferences, val sharedPreferencesEditor: SharedPreferences.Editor) {


    suspend fun authorize(type: String, token: String) {
        when(type) {
            TWITCH_TOKEN -> authorizeTwitch(token)
            YOUTUBE_TOKEN -> authorizeYoutube(token)
            MIXER_TOKEN -> authorizeMixer(token)
        }
    }

    private suspend fun authorizeTwitch(token: String) {
        sharedPreferencesEditor.putString(TWITCH_TOKEN, token).apply()
    }

    private suspend fun authorizeYoutube(token: String) {
        sharedPreferencesEditor.putString(YOUTUBE_TOKEN, token)
    }

    private suspend fun authorizeMixer(token: String) {
        sharedPreferencesEditor.putString(MIXER_TOKEN, token)
    }

   fun getToken(type: String) : String? {
       return sharedPreferences.getString(type, null)}
}