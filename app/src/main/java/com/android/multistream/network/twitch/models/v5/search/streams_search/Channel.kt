package com.android.multistream.network.twitch.models.v5.search.streams_search

import com.squareup.moshi.Json

data class Channel(

    @Json(name = "game")
    val game: String? = null,

    @Json(name = "mature")
    val mature: Boolean? = null,

    @Json(name = "video_banner")
    val videoBanner: String? = null,

    @Json(name = "created_at")
    val createdAt: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "language")
    val language: String? = null,

    @Json(name = "profile_banner_background_color")
    val profileBannerBackgroundColor: String? = null,

    @Json(name = "display_name")
    val displayName: String? = null,

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "broadcaster_type")
    val broadcasterType: String? = null,

    @Json(name = "private_video")
    val privateVideo: Boolean? = null,

    @Json(name = "profile_banner")
    val profileBanner: String? = null,

    @Json(name = "followers")
    val followers: Int? = null,

    @Json(name = "updated_at")
    val updatedAt: String? = null,

    @Json(name = "partner")
    val partner: Boolean? = null,

    @Json(name = "broadcaster_language")
    val broadcasterLanguage: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "logo")
    val logo: String? = null,

    @Json(name = "broadcaster_software")
    val broadcasterSoftware: String? = null,

    @Json(name = "_id")
    val id: Int? = null,

    @Json(name = "views")
    val views: Int? = null,

    @Json(name = "status")
    val status: String? = null,

    @Json(name = "privacy_options_enabled")
    val privacyOptionsEnabled: Boolean? = null
)