package com.android.multistream.network.mixer.models.channel


import com.squareup.moshi.Json


data class ThumbNail(

    @Json(name = "createdAt")
    val createdAt: String? = null,

    @Json(name = "relid")
    val relid: Int? = null,

    @Json(name = "remotePath")
    val remotePath: String? = null,

    @Json(name = "meta")
    val meta: Meta? = null,

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "store")
    val store: String? = null,

    @Json(name = "type")
    val type: String? = null,

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "updatedAt")
    val updatedAt: String? = null
)