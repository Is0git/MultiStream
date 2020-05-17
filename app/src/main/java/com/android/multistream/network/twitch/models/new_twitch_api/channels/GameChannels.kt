package com.android.multistream.network.twitch.models.new_twitch_api.channels


import com.squareup.moshi.Json


data class GameChannels(

    @Json(name = "pagination")
    val pagination: Pagination? = null,

    @Json(name = "data")
    val data: MutableList<DataItem>? = null
)