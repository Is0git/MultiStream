package com.android.multistream.network.mixer.models.channel


import com.squareup.moshi.Json


data class ThumbNail(

    @field:Json(name = "createdAt")
    val createdAt: String? = null,

    @field:Json(name = "relid")
    val relid: Int? = null,

    @field:Json(name = "remotePath")
    val remotePath: String? = null,

    @field:Json(name = "meta")
    val meta: Meta? = null,

    @field:Json(name = "id")
    val id: Int? = null,

    @field:Json(name = "store")
    val store: String? = null,

    @field:Json(name = "type")
    val type: String? = null,

    @field:Json(name = "url")
    val url: String? = null,

    @field:Json(name = "updatedAt")
    val updatedAt: String? = null
)