package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.adapters.GameSearchesAdapter
import com.android.multistream.network.twitch.constants.CLIENT_ID
import com.android.multistream.network.twitch.models.new_twitch_api.channels.GameChannels
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGame
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGames
import com.android.multistream.network.twitch.models.v5.followed_streams.Followed
import com.android.multistream.network.twitch.models.v5.top_games.TopGamesV5
import com.multistream.multistreamsearchview.search_result.SearchListAdapter
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface TwitchService {
    //NEW TWITCH API
    @GET("helix/games/top")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun getTopGames(@Query("after") after: String?): Response<TopGames>

    @GET("helix/streams")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun getChannels(
        @Query(value = "after") after: String? = null, @Query(value = "first") first: Int, @Query(
            "game_id"
        ) gameId: String?
    ): Response<GameChannels>

    //V5 API
    @GET("kraken/games/top")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun getTopGamesV5(
        @Query(value = "offset") offset: Int, @Query(value = "limit") limit: Int, @Query(
            "api_version"
        ) version: Int = 5
    ): Response<MutableList<TopGame>>

    @GET("kraken/games/top")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun getTopGamesV5Full(
        @Query(value = "offset") offset: Int, @Query(value = "limit") limit: Int, @Query(
            "api_version"
        ) version: Int = 5
    ): Response<TopGamesV5>

    @GET("kraken/streams/followed")
    @Headers("Client-ID: $CLIENT_ID", "Accept: application/vnd.twitchtv.v5+json")
    suspend fun getFollowedStreams(@Header("Authorization") access_token: String, @Query("stream_type") streamType: String = "live"): Response<Followed>

    //SEARCHES

    @GET("kraken/search/channels")
    @Headers("Client-ID: $CLIENT_ID", "Accept: application/vnd.twitchtv.v5+json")
    suspend fun getChannelSearches(
        @Query("query") query: String, @Query("limit") limit: Int, @Query(
            "offset"
        ) offset: Int, @Query("api_version") version: Int = 5
    ): Response<List<SearchViewLayout.SearchData>?>

    @GET("kraken/search/games")
    @Headers("Client-ID: $CLIENT_ID", "Accept: application/vnd.twitchtv.v5+json")
    suspend fun getGamesSearches(
        @Query("query") query: String, @Query("limit") limit: Int = 10, @Query(
            "api_version"
        ) version: Int = 5
    ): Response<List<GameSearchesAdapter.GamesSearchData>?>

    @GET("kraken/search/streams")
    @Headers("Client-ID: $CLIENT_ID", "Accept: application/vnd.twitchtv.v5+json")
    suspend fun getStreamSearches(
        @Query("query") query: String, @Query("limit") limit: Int, @Query(
            "offset"
        ) offset: Int, @Query("hls") isHls: Boolean? = null, @Query("api_version") version: Int = 5
    ): Response<List<SearchListAdapter.StreamSearchData>?>
}