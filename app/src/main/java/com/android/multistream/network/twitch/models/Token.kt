package com.android.multistream.network.twitch.models

import com.squareup.moshi.Json

data class Token(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "refresh_token") val refreshToken: String,
    @Json(name = "expires_in") val expires_in: Int,
    val scope: Array<String>,
    val token_type: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Token

        if (accessToken != other.accessToken) return false
        if (refreshToken != other.refreshToken) return false
        if (expires_in != other.expires_in) return false
        if (!scope.contentEquals(other.scope)) return false
        if (token_type != other.token_type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = accessToken.hashCode()
        result = 31 * result + refreshToken.hashCode()
        result = 31 * result + expires_in
        result = 31 * result + scope.contentHashCode()
        result = 31 * result + token_type.hashCode()
        return result
    }
}
