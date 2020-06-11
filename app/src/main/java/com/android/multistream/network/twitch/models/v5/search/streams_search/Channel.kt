package com.android.multistream.network.twitch.models.v5.search.streams_search

import com.squareup.moshi.Json

data class Channel(

    @field:Json(name = "game")
    val game: String? = null,

    @field:Json(name = "mature")
    val mature: Boolean? = null,

    @field:Json(name = "video_banner")
    val video_banner: String? = null,

    @field:Json(name = "created_at")
    val created_at: String? = null,

    @field:Json(name = "description")
    val description: String? = null,

    @field:Json(name = "language")
    val language: String? = null,

    val profile_banner_background_color: String? = null,

    val display_name: String? = null,

    @field:Json(name = "url")
    val url: String? = null,

    @field:Json(name = "broadcaster_type")
    val broadcasterType: String? = null,

    @field:Json(name = "private_video")
    val privateVideo: Boolean? = null,

    @field:Json(name = "profile_banner")
    val profileBanner: String? = null,

    @field:Json(name = "followers")
    val followers: Int? = null,

    @field:Json(name = "updated_at")
    val updated_at: String? = null,

    @field:Json(name = "partner")
    val partner: Boolean? = null,

    @field:Json(name = "broadcaster_language")
    val broadcaster_language: String? = null,

    @field:Json(name = "name")
    val name: String? = null,

    @field:Json(name = "logo")
    val logo: String? = null,

    @field:Json(name = "broadcaster_software")
    val broadcaster_software: String? = null,

    @field:Json(name = "_id")
    val id: Int? = null,

    @field:Json(name = "views")
    val views: Int? = null,

    @field:Json(name = "status")
    val status: String? = null,

    @field:Json(name = "privacy_options_enabled")
    val privacy_options_enabled: Boolean? = null
)