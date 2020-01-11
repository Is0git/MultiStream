package com.android.multistream.network.twitch.models.channels


import com.squareup.moshi.Json


data class GameChannels(

	@Json(name="pagination")
	val pagination: Pagination? = null,

	@Json(name="data")
	val data: List<DataItem?>? = null
)