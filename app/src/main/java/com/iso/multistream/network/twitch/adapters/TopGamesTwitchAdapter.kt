package com.iso.multistream.network.twitch.adapters

import com.iso.multistream.R
import com.iso.multistream.network.twitch.models.new_twitch_api.top_games.TopGame
import com.iso.multistream.network.twitch.models.v5.search.channels_search.SearchChannels
import com.iso.multistream.network.twitch.models.v5.top_games.TopGamesV5
import com.iso.multistream.utils.TWITCH
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import com.multistream.multistreamsearchview.search_view.SearchViewLayout.Companion.CHANNELS
import com.squareup.moshi.FromJson

class TopGamesTwitchAdapter {

    @FromJson
    fun topGamesTwitch(topGames: TopGamesV5): List<TopGame>? {
        return topGames.top?.map {
            TopGame(
                it.game?.box?.large,
                it.game?._id.toString(),
                it.game?.name,
                0,
                it.viewers!!
            )
        }
    }

    @FromJson
    fun getChannelSearch(searches: SearchChannels): List<SearchViewLayout.SearchData>? {
        return searches.channels?.map {
            SearchViewLayout.SearchData(
                it.id,
                it.name,
                it.logo,
                CHANNELS,
                R.string.channels_category,
                TWITCH,
                R.drawable.twitch
            )
        }
    }
}