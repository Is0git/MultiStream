package com.android.multistream.network.twitch.adapters

import androidx.annotation.DrawableRes
import com.android.multistream.R
import com.android.multistream.network.twitch.models.v5.search.streams_search.StreamSearches
import com.android.multistream.utils.TWITCH
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import com.multistream.multistreamsearchview.search_view.SearchViewLayout.Companion.STREAMS
import com.squareup.moshi.FromJson

class StreamSearchesAdapter {
    @FromJson
    fun getStreamSearches(streamSearches: StreamSearches): List<StreamSearchData>? {
        return streamSearches.streams?.map {
            StreamSearchData(
                it?.channel?.name, it?.preview?.large, TWITCH, R.drawable.twitch
            )
        }
    }

    class StreamSearchData(
        title: String? = null,
        imageUrl: String? = null,
        platform: Int,
        @DrawableRes platformResId: Int
    ) : SearchViewLayout.SearchData(title, imageUrl, STREAMS, R.string.streams_category, TWITCH, R.drawable.twitch)
}