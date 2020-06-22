package com.iso.multistream.network.twitch.adapters

import androidx.annotation.DrawableRes
import com.iso.multistream.R
import com.iso.multistream.network.twitch.models.v5.search.games_search.GameSearches
import com.iso.multistream.utils.TWITCH
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import com.multistream.multistreamsearchview.search_view.SearchViewLayout.Companion.GAMES
import com.squareup.moshi.FromJson

class GameSearchesAdapter {

    @FromJson
    fun getGameSearches(gameSearches: GameSearches): List<GamesSearchData>? {
        return gameSearches.games?.map {
            GamesSearchData(it._id, it.name, it.box?.large, TWITCH, R.drawable.twitch)
        }
    }

    class GamesSearchData(
        id: Int?,
        title: String? = null,
        imageUrl: String? = null,
        platform: Int,
        @DrawableRes platformResId: Int
    ) : SearchViewLayout.SearchData(
        id,
        title,
        imageUrl,
        GAMES,
        R.string.games_category,
        platform,
        platformResId
    )
}