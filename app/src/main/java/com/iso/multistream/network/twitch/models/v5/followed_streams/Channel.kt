package com.iso.multistream.network.twitch.models.v5.followed_streams


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Channel(
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
    val display_name: String? = null,
    @field:Json(name = "url")
    val url: String? = null,
    @field:Json(name = "followers")
    val followers: Int? = null,
    val broadcast_platform: String? = null,
    val profile_banner: String? = null,
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
    val _id: Int? = null,
    @field:Json(name = "views")
    val views: Int? = null,
    @field:Json(name = "status")
    val status: String? = null
) : Parcelable