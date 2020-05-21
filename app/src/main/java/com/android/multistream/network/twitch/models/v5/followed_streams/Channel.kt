package com.android.multistream.network.twitch.models.v5.followed_streams


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Channel(

    @Json(name = "game")
    val game: String? = null,

    @Json(name = "mature")
    val mature: Boolean? = null,

    @Json(name = "video_banner")
    val videoBanner: String? = null,

    @Json(name = "created_at")
    val createdAt: String? = null,

    @Json(name = "language")
    val language: String? = null,

    val display_name: String? = null,

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "followers")
    val followers: Int? = null,

    val broadcast_platform: String? = null,

    val profile_banner: String? = null,

    @Json(name = "partner")
    val partner: Boolean? = null,

    @Json(name = "updated_at")
    val updatedAt: String? = null,

    @Json(name = "broadcaster_language")
    val broadcasterLanguage: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "logo")
    val logo: String? = null,

    val _id: Int? = null,

    @Json(name = "views")
    val views: Int? = null,

    @Json(name = "status")
    val status: String? = null
) : Parcelable