package com.iso.multistream.network.twitch.models.v5.followed_streams


import com.squareup.moshi.Json


data class Followed(

    @field:Json(name = "streams")
    val streams: MutableList<StreamsItem>? = null
)