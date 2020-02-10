package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.models.Data
import com.android.multistream.network.twitch.models.TopGames
import com.android.multistream.network.twitch.models.channels.GameChannels
import com.android.multistream.network.twitch.models.v5.TopGamesV5
import com.android.multistream.utils.twitchAPI.client_id
import retrofit2.Response
import retrofit2.http.*

interface TwitchService {

    @GET("helix/games/top")
    @Headers("Client-ID: $client_id")
    suspend fun getTopGames(@Query("after") after: String?) : Response<TopGames>

    //v5 api
    @GET("kraken/games/top")
    @Headers("Client-ID: $client_id")
    suspend fun getTopGamesV5(@Query(value = "offset") offset: Int, @Query(value = "limit") limit: Int, @Query("api_version") version: Int = 5) : Response<List<Data>>

    @GET("kraken/games/top")
    @Headers("Client-ID: $client_id")

    suspend fun getTopGamesV5Full(@Query(value = "offset") offset: Int, @Query(value = "limit") limit: Int, @Query("api_version") version: Int = 5) : Response<TopGamesV5>

    @GET("helix/streams")
    @Headers("Client-ID: $client_id")
    suspend fun getChannels(@Query(value = "after") after: String?, @Query(value = "first") first: Int, @Query("game_id") gameId: String?) : Response<GameChannels>

}