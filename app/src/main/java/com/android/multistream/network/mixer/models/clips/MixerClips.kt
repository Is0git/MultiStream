package com.android.multistream.network.mixer.models.clips

import com.squareup.moshi.Json

data class MixerClips(

	@field:Json(name="contentMaturity")
	val contentMaturity: Int? = null,

	@field:Json(name="ownerChannelId")
	val ownerChannelId: Int? = null,

	@field:Json(name="durationInSeconds")
	val durationInSeconds: Int? = null,

	@field:Json(name="contentId")
	val contentId: String? = null,

	@field:Json(name="title")
	val title: String? = null,

	@field:Json(name="shareableId")
	val shareableId: String? = null,

	@field:Json(name="tags")
	val tags: List<Any?>? = null,

	@field:Json(name="contentLocators")
	val contentLocators: List<ContentLocatorsItem?>? = null,

	@field:Json(name="uploadDate")
	val uploadDate: String? = null,

	@field:Json(name="streamerChannelId")
	val streamerChannelId: Int? = null,

	@field:Json(name="typeId")
	val typeId: Int? = null,

	@field:Json(name="viewCount")
	val viewCount: Int? = null,

	@field:Json(name="expirationDate")
	val expirationDate: String? = null
)