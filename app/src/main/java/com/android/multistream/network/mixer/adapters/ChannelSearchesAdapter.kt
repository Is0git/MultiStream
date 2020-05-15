package com.android.multistream.network.mixer.adapters

import com.android.multistream.R
import com.android.multistream.network.mixer.models.search.channel_search.ChannelSearches
import com.android.multistream.utils.MIXER
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import com.squareup.moshi.FromJson

class ChannelSearchesAdapter {
    @FromJson
    fun getChannelSearches(channelSearches: List<ChannelSearches>): List<SearchViewLayout.SearchData>? {
        return channelSearches.map {
            SearchViewLayout.SearchData(
                it.user?.username,
                it.user?.avatarUrl,
                SearchViewLayout.CHANNELS,
                R.string.channels_category,
                MIXER,
                R.drawable.mixer_small_logo
            )
        }
    }
}