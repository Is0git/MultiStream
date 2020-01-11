package com.android.multistream.network.twitch.models.channels

import javax.annotation.Generated
import com.squareup.moshi.Json

@Generated("com.robohorse.robopojogenerator")
data class GameChannels(

	@Json(name="pagination")
	val pagination: Pagination? = null,

	@Json(name="data")
	val data: List<DataItem?>? = null
)