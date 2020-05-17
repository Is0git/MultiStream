package com.android.multistream.network.twitch.adapters

import com.android.multistream.R
import com.android.multistream.network.twitch.models.v5.search.streams_search.StreamSearches
import com.android.multistream.utils.TWITCH
import com.multistream.multistreamsearchview.search_result.SearchListAdapter
import com.squareup.moshi.FromJson

class StreamSearchesAdapter {
    @FromJson
    fun getStreamSearches(streamSearches: StreamSearches): List<SearchListAdapter.StreamSearchData>? {
        return streamSearches.streams?.map {
            SearchListAdapter.StreamSearchData(
                it?.channel?.name, it?.preview?.large, it?.viewers ?: 0, TWITCH, R.drawable.twitch
            )
        }
    }
}