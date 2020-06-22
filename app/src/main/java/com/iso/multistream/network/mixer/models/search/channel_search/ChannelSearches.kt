package com.iso.multistream.network.mixer.models.search.channel_search

import com.squareup.moshi.Json

data class ChannelSearches(

    @field:Json(name = "featured")
    val featured: Boolean? = null,

    @field:Json(name = "viewersTotal")
    val viewersTotal: Int? = null,

    @field:Json(name = "viewersCurrent")
    val viewersCurrent: Int? = null,

    @field:Json(name = "badgeId")
    val badgeId: Any? = null,

    @field:Json(name = "hasTranscodes")
    val hasTranscodes: Boolean? = null,

    @field:Json(name = "interactive")
    val interactive: Boolean? = null,

    @field:Json(name = "bannerUrl")
    val bannerUrl: String? = null,

    @field:Json(name = "description")
    val description: String? = null,

    @field:Json(name = "type")
    val type: Type? = null,

    @field:Json(name = "createdAt")
    val createdAt: String? = null,

    @field:Json(name = "coverId")
    val coverId: Any? = null,

    @field:Json(name = "hasVod")
    val hasVod: Boolean? = null,

    @field:Json(name = "transcodingProfileId")
    val transcodingProfileId: Any? = null,

    @field:Json(name = "numFollowers")
    val numFollowers: Int? = null,

    @field:Json(name = "thumbnailId")
    val thumbnailId: Any? = null,

    @field:Json(name = "id")
    val id: Int? = null,

    @field:Json(name = "hosteeId")
    val hosteeId: Any? = null,

    @field:Json(name = "partnered")
    val partnered: Boolean? = null,

    @field:Json(name = "ftl")
    val ftl: Int? = null,

    @field:Json(name = "updatedAt")
    val updatedAt: String? = null,

    @field:Json(name = "audience")
    val audience: String? = null,

    @field:Json(name = "thumbnail")
    val thumbnail: String? = null,

    @field:Json(name = "interactiveGameId")
    val interactiveGameId: Any? = null,

    @field:Json(name = "languageId")
    val languageId: String? = null,

    @field:Json(name = "userId")
    val userId: Int? = null,

    @field:Json(name = "featureLevel")
    val featureLevel: Int? = null,

    @field:Json(name = "suspended")
    val suspended: Boolean? = null,

    @field:Json(name = "token")
    val token: String? = null,

    @field:Json(name = "deletedAt")
    val deletedAt: Any? = null,

    @field:Json(name = "vodsEnabled")
    val vodsEnabled: Boolean? = null,

    @field:Json(name = "costreamId")
    val costreamId: Any? = null,

    @field:Json(name = "name")
    val name: String? = null,

    @field:Json(name = "online")
    val online: Boolean? = null,

    @field:Json(name = "typeId")
    val typeId: Int? = null,

    @field:Json(name = "user")
    val user: User? = null
)