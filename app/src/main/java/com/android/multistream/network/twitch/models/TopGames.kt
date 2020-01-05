package com.android.multistream.network.twitch.models

import com.squareup.moshi.Json

data class TopGames(@Json(name = "data") val data: List<Data>)

data class Data(val id: String, val name: String, val pagination: String) {
}