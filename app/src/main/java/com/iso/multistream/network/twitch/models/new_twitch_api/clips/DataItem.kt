package com.iso.multistream.network.twitch.models.new_twitch_api.clips

import com.squareup.moshi.Json

data class DataItem(

	val embed_url: String? = null,

	val broadcaster_id: String? = null,

	val created_at: String? = null,

	@field:Json(name="language")
	val language: String? = null,

	val broadcaster_name: String? = null,

	@field:Json(name="title")
	val title: String? = null,

	val thumbnail_url: String? = null,

	@field:Json(name="url")
	val url: String? = null,

	val creator_id: String? = null,

	val creator_name: String? = null,

	@field:Json(name="id")
	val id: String? = null,

	val view_count: Int? = null,

	val video_id: String? = null,

	val game_id: String? = null
)