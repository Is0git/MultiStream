package com.iso.multistream.network.twitch.models.auth


import com.squareup.moshi.Json


data class Validation(

    @field:Json(name = "user_id")
    val userId: String? = null,

    @field:Json(name = "scopes")
    val scopes: List<String?>? = null,

    @field:Json(name = "login")
    val login: String? = null,

    @field:Json(name = "client_id")
    val clientId: String? = null
)