package com.iso.multistream.network.twitch.models.v5.search.streams_search

import com.squareup.moshi.Json

data class StreamsItem(

    @field:Json(name = "preview")
    val preview: Preview? = null,

    @field:Json(name = "video_height")
    val videoHeight: Int? = null,

    @field:Json(name = "game")
    val game: String? = null,

    @field:Json(name = "average_fps")
    val averageFps: Int? = null,

    @field:Json(name = "channel")
    val channel: Channel? = null,

    @field:Json(name = "created_at")
    val createdAt: String? = null,

    @field:Json(name = "is_playlist")
    val isPlaylist: Boolean? = null,

    @field:Json(name = "broadcast_platform")
    val broadcastPlatform: String? = null,

    @field:Json(name = "community_id")
    val communityId: String? = null,

    @field:Json(name = "viewers")
    val viewers: Int? = null,

    @field:Json(name = "delay")
    val delay: Int? = null,

    @field:Json(name = "community_ids")
    val communityIds: List<Any?>? = null,

    @field:Json(name = "stream_type")
    val streamType: String? = null,

    @field:Json(name = "_id")
    val id: String? = null
)