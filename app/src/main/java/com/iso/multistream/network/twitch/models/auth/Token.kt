package com.iso.multistream.network.twitch.models.auth

import com.squareup.moshi.Json

data class Token(
    val access_token: String,
    val refresh_token: String,
    @field:Json(name = "expires_in") val expires_in: Int,
    val scope: Array<String>,
    val token_type: String
)
