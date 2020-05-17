package com.android.multistream.network.twitch.models.v5.featured_streams


import com.squareup.moshi.Json


data class Featured(

    @Json(name = "featured")
    val featured: MutableList<FeaturedItem>? = null
)