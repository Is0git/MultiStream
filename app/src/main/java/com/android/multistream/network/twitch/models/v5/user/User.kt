package com.android.multistream.network.twitch.models.v5.user

import com.squareup.moshi.Json

data class User(

	@Json(name="updated_at")
	val updatedAt: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="bio")
	val bio: String? = null,

	@Json(name="created_at")
	val createdAt: String? = null,

	@Json(name="logo")
	val logo: String? = null,

	@Json(name="_id")
	val id: String? = null,

	@Json(name="display_name")
	val displayName: String? = null,

	@Json(name="type")
	val type: String? = null
)