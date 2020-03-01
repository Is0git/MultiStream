package com.android.multistream.network.twitch.models.v5.top_games

import com.android.multistream.network.twitch.models.v5.top_games.Box
import com.android.multistream.network.twitch.models.v5.top_games.Logo

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
