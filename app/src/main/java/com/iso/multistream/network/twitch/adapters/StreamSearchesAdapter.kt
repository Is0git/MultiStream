package com.iso.multistream.network.twitch.adapters

import androidx.annotation.DrawableRes
import com.iso.multistream.R
import com.iso.multistream.network.twitch.models.v5.search.streams_search.StreamSearches
import com.iso.multistream.utils.TWITCH
import com.multistream.multistreamsearchview.search_result.SearchListAdapter
import com.squareup.moshi.FromJson

class StreamSearchesAdapter {
    @FromJson
    fun getStreamSearches(streamSearches: StreamSearches): List<StreamSearchesAdapter.LiveStreamSearchData>? {
        streamSearches
        return streamSearches.streams?.map {
            LiveStreamSearchData(
                0,
                it.channel?.name,
                it.preview?.large,
                it.viewers ?: 0,
                TWITCH,
                R.drawable.twitch
            )
        }
    }

    class LiveStreamSearchData(
        id: Int? = null,
        title: String? = null,
        imageUrl: String? = null,
        viewers: Int = 0,
        platform: Int,
        @DrawableRes platformResId: Int
    ) : SearchListAdapter.StreamSearchData(
        id,
        title,
        imageUrl,
        viewers,
        platform,
        platformResId

    )
}