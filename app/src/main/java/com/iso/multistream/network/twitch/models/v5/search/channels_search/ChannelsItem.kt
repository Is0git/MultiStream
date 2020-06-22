package com.iso.multistream.network.twitch.models.v5.search.channels_search

import com.squareup.moshi.Json

data class ChannelsItem(

    @field:Json(name = "game")
    val game: String? = null,

    @field:Json(name = "mature")
    val mature: Boolean? = null,

    @field:Json(name = "video_banner")
    val videoBanner: String? = null,

    @field:Json(name = "created_at")
    val createdAt: String? = null,

    @field:Json(name = "language")
    val language: String? = null,

    @field:Json(name = "profile_banner_background_color")
    val profileBannerBackgroundColor: String? = null,

    @field:Json(name = "display_name")
    val displayName: String? = null,

    @field:Json(name = "url")
    val url: String? = null,

    @field:Json(name = "followers")
    val followers: Int? = null,

    @field:Json(name = "profile_banner")
    val profileBanner: String? = null,

    @field:Json(name = "partner")
    val partner: Boolean? = null,

    @field:Json(name = "updated_at")
    val updatedAt: String? = null,

    @field:Json(name = "broadcaster_language")
    val broadcasterLanguage: String? = null,

    @field:Json(name = "name")
    val name: String? = null,

    @field:Json(name = "logo")
    val logo: String? = null,

    @field:Json(name = "_id")
    val id: Int? = null,

    @field:Json(name = "views")
    val views: Int? = null,

    @field:Json(name = "status")
    val status: String? = null
)