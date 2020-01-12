package com.android.multistream.network.twitch.models

import android.net.Uri
import android.os.Parcelable
import com.android.multistream.network.twitch.models.v5.TopGamesV5
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class TopGames(@Json(name = "data") val data: MutableList<Data>, val pagination: Pagination?)

@Parcelize
data class Data(val box_art_url: String?, val id: String?, val name: String?, val platformType: String?, val viewersCount: Int, val mixerCount: Int? = null) : Parcelable

data class twitchData(val box_art_url: String?, val id: String?, val name: String?, val platformType: String?, val viewersCount: Int, val mixerCount: Int? = null)

data class mixerData(val box_art_url: String?, val id: String?, val name: String?, val platformType: String?, val viewersCount: Int, val mixerCount: Int? = null)

data class Pagination(val cursor: String?)

data class BoxArtUrl(val template: String?) {

}