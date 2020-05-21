package com.android.multistream.network.twitch.models.new_twitch_api.videos

import com.squareup.moshi.Json

data class TwitchVideos(

	@Json(name="pagination")
	val pagination: Pagination? = null,

	@Json(name="data")
	val data: List<DataItem>? = null
)