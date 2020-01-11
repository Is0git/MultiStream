package com.android.multistream.network.twitch.models.channels

import javax.annotation.Generated
import com.squareup.moshi.Json

@Generated("com.robohorse.robopojogenerator")
data class DataItem(

	@Json(name="user_id")
	val userId: String? = null,

	@Json(name="user_name")
	val userName: String? = null,

	@Json(name="started_at")
	val startedAt: String? = null,

	@Json(name="language")
	val language: String? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="viewer_count")
	val viewerCount: Int? = null,

	@Json(name="type")
	val type: String? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="thumbnail_url")
	val thumbnailUrl: String? = null,

	@Json(name="game_id")
	val gameId: String? = null
)