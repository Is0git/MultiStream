package com.android.multistream.network.mixer.models.vods

import com.squareup.moshi.Json

data class VodsItem(

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="baseUrl")
	val baseUrl: String? = null,

	@Json(name="data")
	val data: Data? = null,

	@Json(name="format")
	val format: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="recordingId")
	val recordingId: Int? = null,

	@Json(name="updatedAt")
	val updatedAt: String? = null
)