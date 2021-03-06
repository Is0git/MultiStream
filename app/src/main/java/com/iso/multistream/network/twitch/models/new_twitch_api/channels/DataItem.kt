package com.iso.multistream.network.twitch.models.new_twitch_api.channels


import com.iso.multistream.network.twitch.models.v5.user.User
import com.squareup.moshi.Json


data class DataItem(

    val user_id: String? = null,

    @field:Json(name = "user_name")
    val user_name: String? = null,

    val started_at: String? = null,

    @field:Json(name = "language")
    val language: String? = null,

    @field:Json(name = "id")
    val id: String? = null,

    @field:Json(name = "viewer_count")
    val viewer_count: Int? = null,

    @field:Json(name = "type")
    val type: String? = null,

    @field:Json(name = "title")
    val title: String? = null,

    val thumbnail_url: String? = null,

    @field:Json(name = "game_id")
    val gameId: String? = null,

    @Transient
    var user: User? = null
)