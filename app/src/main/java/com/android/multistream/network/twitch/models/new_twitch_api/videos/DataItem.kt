package com.android.multistream.network.twitch.models.new_twitch_api.videos

import com.squareup.moshi.Json
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Url

data class DataItem(

	val user_name: String? = null,

	@Json(name="description")
	val description: String? = null,

	val created_at: String? = null,

	@Json(name="language")
	val language: String? = null,

	@Json(name="title")
	val title: String? = null,

	val thumbnail_url: String? = null,

	@Json(name="type")
	val type: String? = null,

	@Json(name="url")
	val url: String? = null,

	@Json(name="duration")
	val duration: String? = null,

	@Json(name="viewable")
	val viewable: String? = null,

	val user_id: String? = null,

	@Json(name="id")
	val id: String? = null,

	val published_at: String? = null,

	@Json(name="view_count")
	val view_count: Int? = null
)