package com.iso.multistream.network.mixer.models.search.channel_search

import com.squareup.moshi.Json

data class User(

    @field:Json(name = "sparks")
    val sparks: Int? = null,

    @field:Json(name = "level")
    val level: Int? = null,

    @field:Json(name = "social")
    val social: Social? = null,

    @field:Json(name = "avatarUrl")
    val avatarUrl: String? = null,

    @field:Json(name = "verified")
    val verified: Boolean? = null,

    @field:Json(name = "bio")
    val bio: Any? = null,

    @field:Json(name = "experience")
    val experience: Int? = null,

    @field:Json(name = "createdAt")
    val createdAt: String? = null,

    @field:Json(name = "deletedAt")
    val deletedAt: Any? = null,

    @field:Json(name = "primaryTeam")
    val primaryTeam: Any? = null,

    @field:Json(name = "id")
    val id: Int? = null,

    @field:Json(name = "username")
    val username: String? = null,

    @field:Json(name = "updatedAt")
    val updatedAt: String? = null
)