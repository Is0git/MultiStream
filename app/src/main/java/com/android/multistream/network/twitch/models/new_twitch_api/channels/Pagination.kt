package com.android.multistream.network.twitch.models.new_twitch_api.channels


import com.squareup.moshi.Json


data class Pagination(

	@Json(name="cursor")
	val cursor: String? = null
)