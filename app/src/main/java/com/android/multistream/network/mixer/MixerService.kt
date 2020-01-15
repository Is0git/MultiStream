package com.android.multistream.network.mixer

import com.android.multistream.network.mixer.models.channel.GameChannels
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.network.twitch.models.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MixerService {
    @GET(value = "types")
    suspend fun getMixerTopGames(@Query("where")where: String?, @Query("order") order: String?, @Query(value = "limit") limit: Int?, @Query(value = "page") page: Int?) : Response<List<Data>>




    @GET(value = "types")
    suspend fun getMixerTopGamesFull(@Query("order") order: String?, @Query(value = "limit") limit: Int?, @Query(value = "page") page: Int?) : Response<List<MixerTopGames>>

    @GET(value = "types")
    suspend fun getMixerTopGame(@Query("where") where: String?, @Query(value = "limit") limit: Int?) : Response<List<MixerTopGames>>


    @GET("types/{id}/channels")
    suspend fun getGameChannels(@Path(value = "id") gameId: String?,  @Query(value = "page") page: Int?, @Query(value = "limit") limit: Int, @Query("order") order: String? = "DESC") : Response<List<GameChannels>>
}