package com.android.multistream.network.mixer.models.vods

import com.squareup.moshi.Json

data class Data(

	@Json(name="Has720pPreview")
	val has720pPreview: Boolean? = null,

	@Json(name="Fps")
	val fps: Double? = null,

	@Json(name="Height")
	val height: Int? = null,

	@Json(name="Width")
	val width: Int? = null,

	@Json(name="Bitrate")
	val bitrate: Int? = null
)