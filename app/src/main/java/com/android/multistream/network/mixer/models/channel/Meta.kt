package com.android.multistream.network.mixer.models.channel


import com.squareup.moshi.Json


data class Meta(

    @field:Json(name = "size")
    val size: List<Int>? = null
)