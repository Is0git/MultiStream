package com.iso.multistream.network.twitch.models.new_twitch_api.clips

import com.iso.multistream.network.twitch.models.new_twitch_api.videos.Pagination
import com.squareup.moshi.Json

data class Clips(

	@field:Json(name="data")
	val data: List<DataItem>? = null,

	var pagination: Pagination? = null
)