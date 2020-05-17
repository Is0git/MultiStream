package com.android.multistream.network.twitch.models.v5.featured_streams


import com.squareup.moshi.Json


data class FeaturedItem(

    @Json(name = "image")
    val image: String? = null,

    @Json(name = "stream")
    val stream: Stream? = null,

    @Json(name = "scheduled")
    val scheduled: Boolean? = null,

    @Json(name = "sponsored")
    val sponsored: Boolean? = null,

    @Json(name = "text")
    val text: String? = null,

    @Json(name = "priority")
    val priority: Int? = null,

    @Json(name = "title")
    val title: String? = null
)