package com.android.multistream.network.mixer.models.clips

import com.squareup.moshi.Json

data class ContentLocatorsItem(

	@Json(name="locatorType")
	val locatorType: String? = null,

	@Json(name="uri")
	val uri: String? = null
)