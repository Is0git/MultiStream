package com.android.multistream.network.mixer.models.channel

import com.android.multistream.network.mixer.models.channel.Social

data class User(
    val sparks: Int? = null,
    val level: Int? = null,
    val social: Social? = null,
    val avatarUrl: Any? = null,
    val verified: Boolean? = null,
    val bio: Any? = null,
    val experience: Int? = null,
    val createdAt: String? = null,
    val deletedAt: Any? = null,
    val primaryTeam: Any? = null,
    val id: Int? = null,
    val username: String? = null,
    val updatedAt: String? = null
)
