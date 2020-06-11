package com.android.multistream.network.mixer.models.search.channel_search

import com.squareup.moshi.Json

data class Type(

    @field:Json(name = "coverUrl")
    val coverUrl: String? = null,

    @field:Json(name = "backgroundUrl")
    val backgroundUrl: String? = null,

    @field:Json(name = "parent")
    val parent: String? = null,

    @field:Json(name = "viewersCurrent")
    val viewersCurrent: Int? = null,

    @field:Json(name = "availableAt")
    val availableAt: Any? = null,

    @field:Json(name = "name")
    val name: String? = null,

    @field:Json(name = "description")
    val description: String? = null,

    @field:Json(name = "online")
    val online: Int? = null,

    @field:Json(name = "id")
    val id: Int? = null,

    @field:Json(name = "source")
    val source: String? = null
)