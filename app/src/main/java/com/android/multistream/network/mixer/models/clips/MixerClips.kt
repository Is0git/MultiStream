package com.android.multistream.network.mixer.models.clips

import com.squareup.moshi.Json

data class MixerClips(

	@Json(name="contentMaturity")
	val contentMaturity: Int? = null,

	@Json(name="ownerChannelId")
	val ownerChannelId: Int? = null,

	@Json(name="durationInSeconds")
	val durationInSeconds: Int? = null,

	@Json(name="contentId")
	val contentId: String? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="shareableId")
	val shareableId: String? = null,

	@Json(name="tags")
	val tags: List<Any?>? = null,

	@Json(name="contentLocators")
	val contentLocators: List<ContentLocatorsItem?>? = null,

	@Json(name="uploadDate")
	val uploadDate: String? = null,

	@Json(name="streamerChannelId")
	val streamerChannelId: Int? = null,

	@Json(name="typeId")
	val typeId: Int? = null,

	@Json(name="viewCount")
	val viewCount: Int? = null,

	@Json(name="expirationDate")
	val expirationDate: String? = null
)