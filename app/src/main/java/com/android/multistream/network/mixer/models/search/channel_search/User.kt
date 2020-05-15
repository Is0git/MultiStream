package com.android.multistream.network.mixer.models.search.channel_search

import com.android.multistream.network.mixer.models.search.channel_search.Social
import com.squareup.moshi.Json

data class User(

    @Json(name="sparks")
	val sparks: Int? = null,

    @Json(name="level")
	val level: Int? = null,

    @Json(name="social")
	val social: Social? = null,

    @Json(name="avatarUrl")
	val avatarUrl: String? = null,

    @Json(name="verified")
	val verified: Boolean? = null,

    @Json(name="bio")
	val bio: Any? = null,

    @Json(name="experience")
	val experience: Int? = null,

    @Json(name="createdAt")
	val createdAt: String? = null,

    @Json(name="deletedAt")
	val deletedAt: Any? = null,

    @Json(name="primaryTeam")
	val primaryTeam: Any? = null,

    @Json(name="id")
	val id: Int? = null,

    @Json(name="username")
	val username: String? = null,

    @Json(name="updatedAt")
	val updatedAt: String? = null
)