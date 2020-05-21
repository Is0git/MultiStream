package com.android.multistream.network.mixer.models.mixer_channels

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MixerGameChannel(

	@Json(name="featured")
	val featured: Boolean? = null,

	@Json(name="viewersTotal")
	val viewersTotal: Int? = null,

	@Json(name="viewersCurrent")
	val viewersCurrent: Int = 0,

	@Json(name="badgeId")
	val badgeId: Int? = null,

	@Json(name="hasTranscodes")
	val hasTranscodes: Boolean? = null,

	@Json(name="interactive")
	val interactive: Boolean? = null,

	@Json(name="bannerUrl")
	val bannerUrl: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="type")
	val type: Type? = null,

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="coverId")
	val coverId: Int? = null,

	@Json(name="hasVod")
	val hasVod: Boolean? = null,

	@Json(name="transcodingProfileId")
	val transcodingProfileId: Int? = null,

	@Json(name="numFollowers")
	val numFollowers: Int? = null,

	@Json(name="thumbnailId")
	val thumbnailId: Int? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="partnered")
	val partnered: Boolean? = null,

	@Json(name="ftl")
	val ftl: Int? = null,

	@Json(name="updatedAt")
	val updatedAt: String? = null,

	@Json(name="audience")
	val audience: String? = null,

	@Json(name="thumbnail")
	val thumbnail: Thumbnail? = null,

	@Json(name="languageId")
	val languageId: String? = null,

	@Json(name="userId")
	val userId: Int? = null,

	@Json(name="featureLevel")
	val featureLevel: Int? = null,

	@Json(name="suspended")
	val suspended: Boolean? = null,

	@Json(name="token")
	val token: String? = null,

	@Json(name="vodsEnabled")
	val vodsEnabled: Boolean? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="online")
	val online: Boolean? = null,

	@Json(name="typeId")
	val typeId: Int = 0,

	@Json(name="user")
	val user: User? = null
) : Parcelable