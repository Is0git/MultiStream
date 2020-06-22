package com.iso.multistream.network.twitch.models.v5.single_stream

import com.squareup.moshi.Json

data class SingleStream(

	@field:Json(name="stream")
	var stream: Stream? = null
)