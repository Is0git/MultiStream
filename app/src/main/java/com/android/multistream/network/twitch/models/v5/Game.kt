package com.android.multistream.network.twitch.models.v5

import com.squareup.moshi.Json

data class Game(
	val giantbombId: Int? = null,
	val popularity: Int? = null,
	val name: String? = null,
	val logo: Logo? = null,
	val box: Box? = null,
	val _id: Int? = null,
	val locale: String? = null,
	val localizedName: String? = null
)
