package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.models.Token
import retrofit2.http.Header
import retrofit2.http.POST

interface TwitchAuthService {

    @POST("helix/")
    suspend fun getAuthToken(@Header(value = "Authorization") key: String) : Token

}