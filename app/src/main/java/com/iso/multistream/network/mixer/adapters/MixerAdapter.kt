package com.iso.multistream.network.mixer.adapters

import com.iso.multistream.R
import com.iso.multistream.network.mixer.models.search.channel_search.ChannelSearches
import com.iso.multistream.network.mixer.models.top_games.MixerTopGames
import com.iso.multistream.network.twitch.adapters.GameSearchesAdapter
import com.iso.multistream.network.twitch.models.new_twitch_api.top_games.TopGame
import com.iso.multistream.utils.MIXER
import com.multistream.multistreamsearchview.search_result.SearchListAdapter
import com.squareup.moshi.FromJson

class MixerAdapter {

    @FromJson
    fun getTopGames(topGames: List<MixerTopGames>): List<TopGame> {
        return topGames.map {
            TopGame(
                it.backgroundUrl,
                it.id.toString(),
                it.name,
                MIXER,
                it.viewersCurrent ?: 0
            )
        }
    }

    @FromJson
    fun topGamesSearches(topGames: List<MixerTopGames>): List<GameSearchesAdapter.GamesSearchData>? {
        return topGames.map {
            GameSearchesAdapter.GamesSearchData(
                it.id,
                it.name,
                it.coverUrl
                    ?: "https://static.mixer.com/img/design/stream-categories/png/default.png",
                MIXER,
                R.drawable.mixer_small_logo
            )
        }
    }

    @FromJson
    fun getTopStreamSearches(list: List<ChannelSearches>): List<SearchListAdapter.StreamSearchData?> {
        return list.map {
            SearchListAdapter.StreamSearchData(
                it.id,
                it.name,
                it.type?.backgroundUrl,
                it.viewersCurrent ?: 0,
                MIXER,
                R.drawable.mixer_small_logo
            )
        }
    }
}
