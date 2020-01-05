package com.android.multistream.network.twitch.models

import com.squareup.moshi.Json

data class Token(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "refresh_token") val refreshToken: String,
    @Json(name = "expires_in") val expires_in: Int,
    val scope: String,
    val token_type: String
)
