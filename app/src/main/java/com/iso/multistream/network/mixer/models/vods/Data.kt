package com.iso.multistream.network.mixer.models.vods

import com.squareup.moshi.Json

data class Data(

	@field:Json(name="Has720pPreview")
	val has720pPreview: Boolean? = null,

	@field:Json(name="Fps")
	val fps: Double? = null,

	@field:Json(name="Height")
	val height: Int? = null,

	@field:Json(name="Width")
	val width: Int? = null,

	@field:Json(name="Bitrate")
	val bitrate: Int? = null
)