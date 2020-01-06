package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.models.TopGames
import retrofit2.Response
import retrofit2.http.*

interface TwitchService {

    @GET("helix/games/top")
    @Headers("Client-ID: f0dmag7h9n8tj4710up57pjyooo46q")
    suspend fun getTopGames(@Query("after") after: String?) : Response<TopGames>
}