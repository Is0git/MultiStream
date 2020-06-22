package com.iso.multistream.network.twitch.models.v5.single_stream

import com.squareup.moshi.Json

data class Stream(

	@field:Json(name="preview")
	var preview: Preview? = null,

	@field:Json(name="video_height")
	var videoHeight: Int? = null,

	@field:Json(name="is_playlist")
	var isPlaylist: Boolean? = null,

	@field:Json(name="game")
	var game: String? = null,

	@field:Json(name="viewers")
	var viewers: Int? = null,

	@field:Json(name="delay")
	var delay: Int? = null,

	@field:Json(name="average_fps")
	var averageFps: Int? = null,

	@field:Json(name="channel")
	var channel: Channel? = null,

	@field:Json(name="created_at")
	var createdAt: String? = null,

	@field:Json(name="_id")
	var id: Long? = null
)