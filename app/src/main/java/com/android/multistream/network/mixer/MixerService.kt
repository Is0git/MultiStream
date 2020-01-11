package com.android.multistream.network.mixer

import com.android.multistream.network.mixer.models.channel.GameChannels
import com.android.multistream.network.twitch.models.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MixerService {
    @GET(value = "types")
    suspend fun getMixerTopGames(@Query("where")where: String?, @Query("order") order: String?, @Query(value = "limit") limit: Int?, @Query(value = "page") page: Int?) : Response<List<Data>>

    @GET("api/v1/types/{id}/channels")
    suspend fun getGameChannels(@Path(value = "id") gameId: String) : Response<GameChannels>
}