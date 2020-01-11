package com.android.multistream.network.twitch.models.channels


import com.squareup.moshi.Json


data class Pagination(

	@Json(name="cursor")
	val cursor: String? = null
)