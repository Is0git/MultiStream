package com.android.multistream.network.twitch.models.new_twitch_api.videos

import com.squareup.moshi.Json

data class TwitchVideos(

	@field:Json(name="pagination")
	val pagination: Pagination? = null,

	@field:Json(name="data")
	val data: List<DataItem>? = null
)