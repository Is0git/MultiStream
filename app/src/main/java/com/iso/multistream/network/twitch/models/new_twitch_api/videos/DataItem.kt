package com.iso.multistream.network.twitch.models.new_twitch_api.videos

import com.squareup.moshi.Json

data class DataItem(

	val user_name: String? = null,

	@field:Json(name="description")
	val description: String? = null,

	val created_at: String? = null,

	@field:Json(name="language")
	val language: String? = null,

	@field:Json(name="title")
	val title: String? = null,

	val thumbnail_url: String? = null,

	@field:Json(name="type")
	val type: String? = null,

	@field:Json(name="url")
	val url: String? = null,

	@field:Json(name="duration")
	val duration: String? = null,

	@field:Json(name="viewable")
	val viewable: String? = null,

	val user_id: String? = null,

	@field:Json(name="id")
	val id: String? = null,

	val published_at: String? = null,

	@field:Json(name="view_count")
	val view_count: Int? = null
)