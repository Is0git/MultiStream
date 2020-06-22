package com.iso.multistream.network.twitch.models.v5.featured_streams


import com.squareup.moshi.Json


data class Preview(

    @field:Json(name = "small")
    val small: String? = null,

    @field:Json(name = "template")
    val template: String? = null,

    @field:Json(name = "large")
    val large: String? = null,

    @field:Json(name = "medium")
    val medium: String? = null
)