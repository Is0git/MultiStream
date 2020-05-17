package com.android.multistream.network.twitch.models.v5.featured_streams


import com.squareup.moshi.Json


data class Preview(

    @Json(name = "small")
    val small: String? = null,

    @Json(name = "template")
    val template: String? = null,

    @Json(name = "large")
    val large: String? = null,

    @Json(name = "medium")
    val medium: String? = null
)