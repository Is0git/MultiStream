package com.android.multistream.network.twitch.models.v5.search.channels_search

import com.android.multistream.network.twitch.models.v5.search.channels_search.ChannelsItem
import com.squareup.moshi.Json

data class SearchChannels(

	@Json(name="channels")
	val channels: List<ChannelsItem>? = null,

	@Json(name="_total")
	val total: Int? = null
)