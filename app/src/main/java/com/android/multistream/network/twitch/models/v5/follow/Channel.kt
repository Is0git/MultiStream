package com.android.multistream.network.twitch.models.v5.follow

import com.squareup.moshi.Json

data class Channel(

	@Json(name="game")
	val game: Any? = null,

	@Json(name="mature")
	val mature: Any? = null,

	@Json(name="video_banner")
	val videoBanner: Any? = null,

	@Json(name="created_at")
	val createdAt: String? = null,

	@Json(name="language")
	val language: String? = null,

	@Json(name="profile_banner_background_color")
	val profileBannerBackgroundColor: Any? = null,

	@Json(name="display_name")
	val displayName: String? = null,

	@Json(name="url")
	val url: String? = null,

	@Json(name="followers")
	val followers: Int? = null,

	@Json(name="profile_banner")
	val profileBanner: Any? = null,

	@Json(name="partner")
	val partner: Boolean? = null,

	@Json(name="updated_at")
	val updatedAt: String? = null,

	@Json(name="broadcaster_language")
	val broadcasterLanguage: Any? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="logo")
	val logo: Any? = null,

	@Json(name="_id")
	val id: Int? = null,

	@Json(name="views")
	val views: Int? = null,

	@Json(name="status")
	val status: Any? = null
)