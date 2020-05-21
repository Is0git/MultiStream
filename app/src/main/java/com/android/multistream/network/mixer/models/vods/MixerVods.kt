package com.android.multistream.network.mixer.models.vods

import com.squareup.moshi.Json

data class MixerVods(

	@Json(name="duration")
	val duration: Double? = null,

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="contentId")
	val contentId: String? = null,

	@Json(name="typeId")
	val typeId: Int? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="state")
	val state: String? = null,

	@Json(name="expiresAt")
	val expiresAt: String? = null,

	@Json(name="channelId")
	val channelId: Int? = null,

	@Json(name="viewsTotal")
	val viewsTotal: Int? = null,

	@Json(name="updatedAt")
	val updatedAt: String? = null,

	@Json(name="vods")
	val vods: List<VodsItem>? = null
)