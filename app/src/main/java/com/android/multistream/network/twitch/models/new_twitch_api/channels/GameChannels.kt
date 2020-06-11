package com.android.multistream.network.twitch.models.new_twitch_api.channels


import com.squareup.moshi.Json


data class GameChannels(

    @field:Json(name = "pagination")
    val pagination: Pagination? = null,

    @field:Json(name = "data")
    val data: MutableList<DataItem>? = null
)