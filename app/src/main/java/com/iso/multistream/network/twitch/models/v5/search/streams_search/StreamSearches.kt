package com.iso.multistream.network.twitch.models.v5.search.streams_search

import com.squareup.moshi.Json

data class StreamSearches(

    @field:Json(name = "_total")
    val total: Int? = null,

    @field:Json(name = "streams")
    val streams: List<StreamsItem>? = null
)