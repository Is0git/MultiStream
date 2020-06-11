package com.android.multistream.network.mixer.models.vods

import com.squareup.moshi.Json

data class VodsItem(

	@field:Json(name="createdAt")
	val createdAt: String? = null,

	@field:Json(name="baseUrl")
	val baseUrl: String? = null,

	@field:Json(name="data")
	val data: Data? = null,

	@field:Json(name="format")
	val format: String? = null,

	@field:Json(name="id")
	val id: Int? = null,

	@field:Json(name="recordingId")
	val recordingId: Int? = null,

	@field:Json(name="updatedAt")
	val updatedAt: String? = null
)