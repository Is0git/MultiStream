package com.android.multistream.network.twitch.models.v5.search.streams_search

import com.squareup.moshi.Json

data class StreamsItem(

    @Json(name = "preview")
    val preview: Preview? = null,

    @Json(name = "video_height")
    val videoHeight: Int? = null,

    @Json(name = "game")
    val game: String? = null,

    @Json(name = "average_fps")
    val averageFps: Int? = null,

    @Json(name = "channel")
    val channel: Channel? = null,

    @Json(name = "created_at")
    val createdAt: String? = null,

    @Json(name = "is_playlist")
    val isPlaylist: Boolean? = null,

    @Json(name = "broadcast_platform")
    val broadcastPlatform: String? = null,

    @Json(name = "community_id")
    val communityId: String? = null,

    @Json(name = "viewers")
    val viewers: Int? = null,

    @Json(name = "delay")
    val delay: Int? = null,

    @Json(name = "community_ids")
    val communityIds: List<Any?>? = null,

    @Json(name = "stream_type")
    val streamType: String? = null,

    @Json(name = "_id")
    val id: Int? = null
)