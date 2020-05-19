package com.android.multistream.network.mixer.models.mixer_channels

import com.squareup.moshi.Json

data class Social(

	@Json(name="youtube")
	val youtube: String? = null,

	@Json(name="twitter")
	val twitter: String? = null,

	@Json(name="facebook")
	val facebook: String? = null,

	@Json(name="verified")
	val verified: List<Any?>? = null,

	@Json(name="instagram")
	val instagram: String? = null
)