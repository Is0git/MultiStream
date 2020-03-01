package com.android.multistream.network.twitch.models.v5.featured_streams


import com.squareup.moshi.Json


data class Stream(

	@Json(name="preview")
	val preview: Preview? = null,

	@Json(name="is_playlist")
	val isPlaylist: Boolean? = null,

	@Json(name="video_height")
	val videoHeight: Int? = null,

	@Json(name="game")
	val game: String? = null,

	@Json(name="viewers")
	val viewers: Int? = null,

	@Json(name="delay")
	val delay: Int? = null,

	@Json(name="average_fps")
	val averageFps: Double? = null,

	@Json(name="channel")
	val channel: Channel? = null,

	@Json(name="created_at")
	val createdAt: String? = null,

	@Json(name="_id")
	val id: Long? = null
)