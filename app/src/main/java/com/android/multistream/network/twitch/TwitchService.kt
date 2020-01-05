package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.models.TopGames
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.Headers

interface TwitchService {

    @GET("helix/games/top")
    @Headers("Client-ID: f0dmag7h9n8tj4710up57pjyooo46q")
    suspend fun getTopGames() : Response<TopGames>
}