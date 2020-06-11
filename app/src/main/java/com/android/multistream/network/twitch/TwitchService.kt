package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.adapters.GameSearchesAdapter
import com.android.multistream.network.twitch.adapters.StreamSearchesAdapter
import com.android.multistream.network.twitch.constants.CLIENT_ID
import com.android.multistream.network.twitch.models.new_twitch_api.channels.GameChannels
import com.android.multistream.network.twitch.models.new_twitch_api.clips.Clips
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGame
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGames
import com.android.multistream.network.twitch.models.new_twitch_api.videos.TwitchVideos
import com.android.multistream.network.twitch.models.v5.follow.FollowUser
import com.android.multistream.network.twitch.models.v5.followed_streams.Followed
import com.android.multistream.network.twitch.models.v5.search.streams_search.StreamsItem
import com.android.multistream.network.twitch.models.v5.single_stream.SingleStream
import com.android.multistream.network.twitch.models.v5.top_games.TopGamesV5
import com.android.multistream.network.twitch.models.v5.user.User
import com.multistream.multistreamsearchview.search_result.SearchListAdapter
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import retrofit2.Response
import retrofit2.http.*

interface TwitchService {
    //NEW TWITCH API
    @GET("helix/games/top")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun getTopGames(@Query("after") after: String?): Response<TopGames>

    @GET("helix/streams")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun getChannels(
        @Query(value = "after") after: String? = null,
        @Query(value = "first") first: Int,
        @Query("game_id") gameId: Int?,
        @Header("Authorization") access_token: String?
    ): Response<GameChannels>

    @GET("helix/videos")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun getVideos(
        @Query(value = "after") after: String? = null,
        @Query(value = "first") first: Int,
        @Query("user_id") userId: String?,
        @Query("period") period: String?,
        @Query("sort") sort: String?,
        @Query("type") type: String?,
        @Header("Authorization") access_token: String?
    ): Response<TwitchVideos>

    @GET("helix/clips")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun getClips(
        @Query(value = "after") after: String? = null,
        @Query(value = "first") first: Int,
        @Query("broadcaster_id") broadcasterId: String?,
        @Header("Authorization") access_token: String?
    ): Response<Clips>

    //V5 API
    @GET("kraken/games/top")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun getTopGamesV5(
        @Query(value = "offset") offset: Int,
        @Query(value = "limit") limit: Int,
        @Query("api_version") version: Int = 5
    ): Response<MutableList<TopGame>>

    @GET("kraken/games/top")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun getTopGamesV5Full(
        @Query(value = "offset") offset: Int,
        @Query(value = "limit") limit: Int,
        @Query("api_version") version: Int = 5
    ): Response<TopGamesV5>

    @GET("kraken/streams/followed")
    @Headers("Client-ID: $CLIENT_ID", "Accept: application/vnd.twitchtv.v5+json")
    suspend fun getFollowedStreams(
        @Header("Authorization") access_token: String,
        @Query("stream_type") streamType: String = "live",
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<Followed>

    @GET("kraken/streams/{channelId}")
    @Headers("Client-ID: $CLIENT_ID", "Accept: application/vnd.twitchtv.v5+json")
    suspend fun getStream(@Path("channelId", encoded = true) channelId: String, @Query("stream_type") streamType: String? = "live"): Response<SingleStream?>

    @GET("kraken/users/{userId}")
    @Headers("Client-ID: $CLIENT_ID", "Accept: application/vnd.twitchtv.v5+json")
    suspend fun getUser(@Path("userId", encoded = true) userId: Int?): Response<User>

    //SEARCHES

    @GET("kraken/search/channels")
    @Headers("Client-ID: $CLIENT_ID", "Accept: application/vnd.twitchtv.v5+json")
    suspend fun getChannelSearches(
        @Query("query") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("api_version") version: Int = 5
    ): Response<List<SearchViewLayout.SearchData>?>

    @GET("kraken/search/games")
    @Headers("Client-ID: $CLIENT_ID", "Accept: application/vnd.twitchtv.v5+json")
    suspend fun getGamesSearches(
        @Query("query") query: String,
        @Query("limit") limit: Int = 10,
        @Query("api_version") version: Int = 5
    ): Response<List<GameSearchesAdapter.GamesSearchData>?>

    @GET("kraken/search/streams")
    @Headers("Client-ID: $CLIENT_ID", "Accept: application/vnd.twitchtv.v5+json")
    suspend fun getStreamSearches(
        @Query("query") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("hls") isHls: Boolean? = null,
        @Query("api_version") version: Int = 5
    ): Response<List<StreamSearchesAdapter.LiveStreamSearchData>?>

    @PUT("kraken/users/{userId}/follows/channels/{channelId}")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun followChannel(
        @Path("userId") userId: String? = null,
        @Path("channelId") channelId: String? = null,
        @Header("Authorization") access_token: String,
        @Query("api_version") version: Int = 5
    ): Response<FollowUser>

    @DELETE("kraken/users/{userId}/follows/channels/{channelId}")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun unFollowChannel(
        @Path("userId") userId: String? = null,
        @Path("channelId") channelId: String? = null,
        @Header("Authorization") access_token: String,
        @Query("api_version") version: Int = 5
    ) : Response<Unit>

    @GET("kraken/users/{userId}/follows/channels/{channelId}")
    @Headers("Client-ID: $CLIENT_ID")
    suspend fun checkIfFollows(
        @Path("userId") userId: String? = null,
        @Path("channelId") channelId: String? = null,
        @Query("api_version") version: Int = 5
    ): Response<FollowUser?>
}