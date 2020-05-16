package com.android.multistream.network.mixer.adapters

import com.android.multistream.R
import com.android.multistream.network.mixer.models.search.channel_search.ChannelSearches
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.network.twitch.adapters.GameSearchesAdapter
import com.android.multistream.network.twitch.adapters.StreamSearchesAdapter
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGame
import com.android.multistream.utils.MIXER
import com.multistream.multistreamsearchview.search_result.SearchListAdapter
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import com.multistream.multistreamsearchview.search_view.SearchViewLayout.Companion.CHANNELS
import com.multistream.multistreamsearchview.search_view.SearchViewLayout.Companion.GAMES
import com.squareup.moshi.FromJson

class MixerAdapter {
    @FromJson
    fun getTopGames(topGames:List<MixerTopGames>) : List<TopGame> {
        return  topGames.map { TopGame(it.backgroundUrl, it.id.toString(), it.name, MIXER, it.viewersCurrent ?: 0 ) }
    }

    @FromJson
    fun topGamesSearches(topGames:List<MixerTopGames>) : List<GameSearchesAdapter.GamesSearchData>? {
        return topGames.map {
            GameSearchesAdapter.GamesSearchData(
                it.name,
                it.coverUrl ?: "https://static.mixer.com/img/design/stream-categories/png/default.png",
                MIXER,
                R.drawable.mixer_small_logo
            )
        }
    }

    @FromJson
    fun getTopStreamSearches(list: List<ChannelSearches>) : List<SearchListAdapter.StreamSearchData?> {
        return list.map { SearchListAdapter.StreamSearchData(it.name, it.type?.backgroundUrl, it.viewersCurrent ?: 0, MIXER, R.drawable.mixer_small_logo) }
    }
}
