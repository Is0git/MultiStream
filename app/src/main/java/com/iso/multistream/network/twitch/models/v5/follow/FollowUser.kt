package com.iso.multistream.network.twitch.models.v5.follow

import com.squareup.moshi.Json

data class FollowUser(

	@field:Json(name="channel")
	val channel: Channel? = null,

	@field:Json(name="created_at")
	val createdAt: String? = null,

	@field:Json(name="notifications")
	val notifications: Boolean? = null
)