package com.android.multistream.network.mixer.models.vods

import com.squareup.moshi.Json

data class MixerVods(

	@field:Json(name="duration")
	val duration: Double? = null,

	@field:Json(name="createdAt")
	val createdAt: String? = null,

	@field:Json(name="name")
	val name: String? = null,

	@field:Json(name="contentId")
	val contentId: String? = null,

	@field:Json(name="typeId")
	val typeId: Int? = null,

	@field:Json(name="id")
	val id: Int? = null,

	@field:Json(name="state")
	val state: String? = null,

	@field:Json(name="expiresAt")
	val expiresAt: String? = null,

	@field:Json(name="channelId")
	val channelId: Int? = null,

	@field:Json(name="viewsTotal")
	val viewsTotal: Int? = null,

	@field:Json(name="updatedAt")
	val updatedAt: String? = null,

	@field:Json(name="vods")
	val vods: List<VodsItem>? = null
)