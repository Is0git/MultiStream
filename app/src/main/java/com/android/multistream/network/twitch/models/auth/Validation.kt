package com.android.multistream.network.twitch.models.auth


import com.squareup.moshi.Json


data class Validation(

	@Json(name="user_id")
	val userId: String? = null,

	@Json(name="scopes")
	val scopes: List<String?>? = null,

	@Json(name="login")
	val login: String? = null,

	@Json(name="client_id")
	val clientId: String? = null
)