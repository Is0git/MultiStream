package com.android.multistream.network.twitch.models

import com.squareup.moshi.Json

data class TopGames(@Json(name = "data") val data: List<Data>, val pagination: Pagination?)

data class Data(@Json(name = "box_art_url") val boxArtUrl: String?, val id: String?, val name: String?)

data class Pagination(val cursor: String?)