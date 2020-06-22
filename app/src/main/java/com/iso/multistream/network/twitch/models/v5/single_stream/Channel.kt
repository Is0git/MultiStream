package com.iso.multistream.network.twitch.models.v5.single_stream

import com.squareup.moshi.Json

data class Channel(

	@field:Json(name="game")
	var game: String? = null,

	@field:Json(name="mature")
	var mature: Boolean? = null,

	@field:Json(name="video_banner")
	var videoBanner: String? = null,

	@field:Json(name="created_at")
	var createdAt: String? = null,

	@field:Json(name="language")
	var language: String? = null,

	@field:Json(name="profile_banner_background_color")
	var profileBannerBackgroundColor: Any? = null,

	@field:Json(name="display_name")
	var displayName: String? = null,

	@field:Json(name="url")
	var url: String? = null,

	@field:Json(name="profile_banner")
	var profileBanner: String? = null,

	@field:Json(name="followers")
	var followers: Int? = null,

	@field:Json(name="updated_at")
	var updatedAt: String? = null,

	@field:Json(name="partner")
	var partner: Boolean? = null,

	@field:Json(name="broadcaster_language")
	var broadcasterLanguage: String? = null,

	@field:Json(name="name")
	var name: String? = null,

	@field:Json(name="logo")
	var logo: String? = null,

	@field:Json(name="_id")
	var id: Int? = null,

	@field:Json(name="views")
	var views: Int? = null,

	@field:Json(name="status")
	var status: String? = null
)