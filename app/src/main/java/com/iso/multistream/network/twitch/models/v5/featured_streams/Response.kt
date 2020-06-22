package com.iso.multistream.network.twitch.models.v5.featured_streams


import com.squareup.moshi.Json


data class Featured(

    @field:Json(name = "featured")
    val featured: MutableList<FeaturedItem>? = null
)