package com.iso.multistream.network.twitch.models.v5.single_stream

import com.squareup.moshi.Json

data class Preview(

	@field:Json(name="small")
	var small: String? = null,

	@field:Json(name="template")
	var template: String? = null,

	@field:Json(name="large")
	var large: String? = null,

	@field:Json(name="medium")
	var medium: String? = null
)