package com.android.multistream.network.twitch.models.v5.search.games_search

import com.squareup.moshi.Json

data class GameSearches(

	@Json(name="games")
	val games: List<GamesItem>? = null
)