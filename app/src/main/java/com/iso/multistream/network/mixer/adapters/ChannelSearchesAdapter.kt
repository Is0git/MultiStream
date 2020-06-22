package com.iso.multistream.network.mixer.adapters

import com.iso.multistream.R
import com.iso.multistream.network.mixer.models.search.channel_search.ChannelSearches
import com.iso.multistream.utils.MIXER
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import com.squareup.moshi.FromJson

class ChannelSearchesAdapter {

    @FromJson
    fun getChannelSearches(channelSearches: List<ChannelSearches>): List<SearchViewLayout.SearchData>? {
        return channelSearches.map {
            SearchViewLayout.SearchData(
                it.userId,
                it.user?.username,
                it.user?.avatarUrl ?: "https://mixer.com/api/v1/users/175971128/avatar?w=64&h=64",
                SearchViewLayout.CHANNELS,
                R.string.channels_category,
                MIXER,
                R.drawable.mixer_small_logo
            )
        }
    }
}