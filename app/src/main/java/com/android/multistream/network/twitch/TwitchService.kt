package com.android.multistream.network.twitch

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface TwitchService {

    @POST("helix/")
    suspend fun getAuthToken(@Header(value = "Authorization") key: String) : Token

}