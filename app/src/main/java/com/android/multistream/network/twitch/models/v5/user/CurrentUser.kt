package com.android.multistream.network.twitch.models.v5.user

data class CurrentUser(
    val emailVerified: Boolean? = null,
    val bio: String? = null,
    val createdAt: String? = null,
    val displayName: String? = null,
    val type: String? = null,
    val twitterConnected: Boolean? = null,
    val updatedAt: String? = null,
    val name: String? = null,
    val logo: String? = null,
    val id: Int? = null,
    val email: String? = null,
    val notifications: Notifications? = null,
    val partnered: Boolean? = null
)
