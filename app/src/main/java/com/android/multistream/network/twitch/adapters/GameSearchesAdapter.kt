package com.android.multistream.network.twitch.adapters

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.android.multistream.R
import com.android.multistream.network.twitch.adapters.qualifiers.GamesJsonQualifier
import com.android.multistream.network.twitch.models.v5.search.games_search.GameSearches
import com.android.multistream.utils.TWITCH
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import com.multistream.multistreamsearchview.search_view.SearchViewLayout.Companion.GAMES
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class GameSearchesAdapter {


    @FromJson
    fun getGameSearches(gameSearches: GameSearches): List<GamesSearchData>? {
        return gameSearches.games?.map {
           GamesSearchData(it.name, it.box?.large, TWITCH, R.drawable.twitch)
        }
    }

    class GamesSearchData(
        title: String? = null,
        imageUrl: String? = null,
        platform: Int,
        @DrawableRes platformResId: Int
    ) : SearchViewLayout.SearchData(title, imageUrl, GAMES, R.string.games_category, platform, platformResId)
}