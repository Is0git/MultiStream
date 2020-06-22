package com.iso.multistream.network.mixer.models.mixer_channels

import com.squareup.moshi.Json

data class Meta(

	@field:Json(name="size")
	val size: List<Int?>? = null
)