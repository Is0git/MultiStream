package com.android.multistream.network.mixer.models.mixer_channels

import com.squareup.moshi.Json

data class Social(

	@field:Json(name="youtube")
	val youtube: String? = null,

	@field:Json(name="twitter")
	val twitter: String? = null,

	@field:Json(name="facebook")
	val facebook: String? = null,

	@field:Json(name="verified")
	val verified: List<Any?>? = null,

	@field:Json(name="instagram")
	val instagram: String? = null
)