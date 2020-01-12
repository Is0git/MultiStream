package com.android.multistream.network.twitch.models.v5

import com.android.multistream.network.mixer.models.top_games.MixerTopGames

data class TopItem(
	val game: Game? = null,
	val viewers: Int? = null,
	val channels: Int? = null,
	@Transient
	var mixerTopItem: MixerTopGames?
)
