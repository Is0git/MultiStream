package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.models.Data
import com.android.multistream.network.twitch.models.TopGames
import com.android.multistream.network.twitch.models.channels.GameChannels
import com.android.multistream.network.twitch.models.v5.TopGamesV5
import com.android.multistream.network.twitch.models.v5.TopItem
import retrofit2.Response
import retrofit2.http.*

interface TwitchService {

    @GET("helix/games/top")
    @Headers("Client-ID: f0dmag7h9n8tj4710up57pjyooo46q")
    suspend fun getTopGames(@Query("after") after: String?) : Response<TopGames>

    //v5 api
    @GET("kraken/games/top")
    @Headers("Client-ID: f0dmag7h9n8tj4710up57pjyooo46q")
    suspend fun getTopGamesV5(@Query(value = "offset") offset: Int, @Query(value = "limit") limit: Int, @Query("api_version") version: Int = 5) : Response<List<Data>>

    @GET("kraken/games/top")
    @Headers("Client-ID: f0dmag7h9n8tj4710up57pjyooo46q")

    suspend fun getTopGamesV5Full(@Query(value = "offset") offset: Int, @Query(value = "limit") limit: Int, @Query("api_version") version: Int = 5) : Response<TopGamesV5>

    @GET("helix/streams")
    @Headers("Client-ID: f0dmag7h9n8tj4710up57pjyooo46q")
    suspend fun getChannels(@Query(value = "offset") offset: Int, @Query(value = "limit") limit: Int, @Query("game_id") gameId: String) : Response<GameChannels>

}