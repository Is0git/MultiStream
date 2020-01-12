package com.android.multistream.network.twitch.models.v5

data class TopGamesV5(
	val top: List<TopItem>? = null,
	val total: Int? = null
)
