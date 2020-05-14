package com.android.multistream.network.mixer.adapters

import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGame
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGames
import com.android.multistream.utils.MIXER
import com.squareup.moshi.FromJson

class MixerAdapter {
    @FromJson
    fun getTopGames(topGames:List<MixerTopGames>) : List<TopGame> {
        return  topGames.map { TopGame(it.backgroundUrl, it.id.toString(), it.name, MIXER, it.viewersCurrent ?: 0 ) }
    }
}