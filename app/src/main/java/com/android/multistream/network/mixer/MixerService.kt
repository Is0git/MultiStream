package com.android.multistream.network.mixer

import com.android.multistream.network.mixer.models.MixerTopGames
import com.android.multistream.network.twitch.models.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MixerService {
    @GET(value = "types")
    suspend fun getMixerTopGames(@Query("where")where: String?, @Query("order") order: String?, @Query(value = "limit") limit: Int?) : Response<List<Data>>
}