package com.android.multistream.network.twitch.models.v5.user

import com.android.multistream.network.twitch.models.v5.featured_streams.Stream
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

	val display_name: String? = null,

	@Json(name="type")
	val type: String? = null
) {
	@Transient
	var stream: Stream? = null
}