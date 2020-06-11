package com.android.multistream.network.twitch.models.v5.featured_streams


import com.squareup.moshi.Json


data class FeaturedItem(

    @field:Json(name = "image")
    val image: String? = null,

    @field:Json(name = "stream")
    val stream: Stream? = null,

    @field:Json(name = "scheduled")
    val scheduled: Boolean? = null,

    @field:Json(name = "sponsored")
    val sponsored: Boolean? = null,

    @field:Json(name = "text")
    val text: String? = null,

    @field:Json(name = "priority")
    val priority: Int? = null,

    @field:Json(name = "title")
    val title: String? = null
)