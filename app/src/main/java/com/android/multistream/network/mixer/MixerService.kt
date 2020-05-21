package com.android.multistream.network.mixer

import com.android.multistream.network.mixer.models.channel.GameChannels
import com.android.multistream.network.mixer.models.clips.MixerClips
import com.android.multistream.network.mixer.models.mixer_channels.MixerGameChannel
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.network.mixer.models.vods.MixerVods
import com.android.multistream.network.mixer.models.vods.VodsItem
import com.android.multistream.network.twitch.adapters.GameSearchesAdapter
import com.android.multistream.network.twitch.models.auth.Token
import com.android.multistream.network.twitch.models.auth.Validation
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGame
import com.multistream.multistreamsearchview.search_result.SearchListAdapter
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MixerService {
    @GET(value = "types")
    suspend fun getMixerTopGames(
        @Query("where") where: String?,
        @Query("order") order: String?,
        @Query(value = "limit") limit: Int?,
        @Query(value = "page") page: Int?
    ): Response<List<TopGame>>

    @GET(value = "types")
    suspend fun getMixerTopGamesFull(
        @Query("order") order: String?,
        @Query(value = "limit") limit: Int?,
        @Query(value = "page") page: Int?
    ): Response<List<MixerTopGames>>

    @GET(value = "types")
    suspend fun getMixerTopGame(
        @Query("where") where: String?,
        @Query(value = "limit") limit: Int?
    ): Response<List<MixerTopGames>>

    @GET("types/{id}/channels")
    suspend fun getGameChannels(
        @Path(value = "id") gameId: String?,
        @Query(value = "page") page: Int?,
        @Query(value = "limit") limit: Int,
        @Query("order") order: String? = "DESC"
    ): Response<List<GameChannels>>

    @GET("channels")
    suspend fun getChannels(
        @Query("where", encoded = true) where: String? = null,
        @Query("limit", encoded = true) limit: Int,
        @Query("page", encoded = true) page: Int,
        @Query("order", encoded = false) order: String? = null
    ): Response<List<MixerGameChannel>?>

    @GET("recordings")
    suspend fun getVods(
        @Query("where", encoded = true) where: String? = null,
        @Query("limit", encoded = true) limit: Int,
        @Query("page", encoded = true) page: Int,
        @Query("order", encoded = false) order: String? = null
    ) : Response<List<MixerVods>>

    @GET("/clips/channels/{channelId}")
    suspend fun getClips(@Path("channelId", encoded = true) channelId: Int) : Response<MixerClips>

    //Search
    @GET(value = "types")
    suspend fun getSearchedGames(@Query("query") query: String?, @Query(value = "limit") limit: Int?): Response<List<GameSearchesAdapter.GamesSearchData>?>

    @GET("channels")
    suspend fun getSearchedChannels(
        @Query("q") q: String?,
        @Query(value = "limit") limit: Int?,
        @Query("scope") scope: String? = "all"
    ): Response<List<SearchViewLayout.SearchData>?>

    @GET(value = "channels")
    suspend fun getSearchedStreams(
        @Query("q") query: String?, @Query(value = "limit") limit: Int?, @Query(
            "scope"
        ) scope: String? = "all"
    ): Response<List<SearchListAdapter.StreamSearchData>?>

    //AUTH
    @GET("oauth/token")
    fun getToken(): Response<Token>

    @GET("oauth/token")
    fun getValidation(): Response<Validation>

}