package com.iso.multistream.network.mixer.models.search.channel_search

import com.squareup.moshi.Json

data class Social(

    @Json(name = "verified")
    val verified: List<Any?>? = null
)