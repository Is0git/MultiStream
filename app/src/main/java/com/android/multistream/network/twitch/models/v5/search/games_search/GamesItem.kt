package com.android.multistream.network.twitch.models.v5.search.games_search

import com.squareup.moshi.Json

data class GamesItem(

	@Json(name="giantbomb_id")
	val giantbombId: Int? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="logo")
	val logo: Logo? = null,

	@Json(name="box")
	val box: Box? = null,

	@Json(name="_id")
	val id: Int? = null
)