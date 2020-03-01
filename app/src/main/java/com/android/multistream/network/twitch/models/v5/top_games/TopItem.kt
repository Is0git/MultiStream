package com.android.multistream.network.twitch.models.v5.top_games

import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.network.twitch.models.v5.top_games.Game

data class TopItem(
	val game: Game? = null,
	val viewers: Int? = null,
	val channels: Int? = null,
	@Transient
	var mixerTopItem: MixerTopGames?
)
