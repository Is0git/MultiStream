package com.android.multistream.network.twitch.models.v5

import com.squareup.moshi.Json

data class TopGamesV5(
	@Json(name = "top")
	val top: List<TopItem>? = null,
	@Json(name = "_total")
	val total: Int? = null
)
