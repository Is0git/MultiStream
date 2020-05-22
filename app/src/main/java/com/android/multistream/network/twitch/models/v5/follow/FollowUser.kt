package com.android.multistream.network.twitch.models.v5.follow

import com.squareup.moshi.Json

data class FollowUser(

	@Json(name="channel")
	val channel: Channel? = null,

	@Json(name="created_at")
	val createdAt: String? = null,

	@Json(name="notifications")
	val notifications: Boolean? = null
)