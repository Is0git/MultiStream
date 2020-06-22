package com.iso.multistream.network.mixer.models.clips

import com.squareup.moshi.Json

data class ContentLocatorsItem(

	@field:Json(name="locatorType")
	val locatorType: String? = null,

	@field:Json(name="uri")
	val uri: String? = null
)