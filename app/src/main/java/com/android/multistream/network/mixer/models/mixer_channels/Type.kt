package com.android.multistream.network.mixer.models.mixer_channels

import com.squareup.moshi.Json

data class Type(

	@Json(name="coverUrl")
	val coverUrl: String? = null,

	@Json(name="backgroundUrl")
	val backgroundUrl: String? = null,

	@Json(name="parent")
	val parent: String? = null,

	@Json(name="viewersCurrent")
	val viewersCurrent: Int? = null,

	@Json(name="availableAt")
	val availableAt: Any? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="description")
	val description: Any? = null,

	@Json(name="online")
	val online: Int? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="source")
	val source: String? = null
)