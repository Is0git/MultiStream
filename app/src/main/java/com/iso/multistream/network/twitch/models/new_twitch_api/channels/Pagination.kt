package com.iso.multistream.network.twitch.models.new_twitch_api.channels


import com.squareup.moshi.Json


data class Pagination(

    @field:Json(name = "cursor")
    val cursor: String? = null
)