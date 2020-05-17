package com.android.multistream.network.twitch.models.v5.search.streams_search

import com.squareup.moshi.Json

data class StreamSearches(

    @Json(name = "_total")
    val total: Int? = null,

    @Json(name = "streams")
    val streams: List<StreamsItem?>? = null
)