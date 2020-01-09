package com.android.multistream.network.mixer.models

import com.squareup.moshi.Json

data class MixerTopGames(
    @Json(name = "id")  val id: Int?,
    @Json(name = "name")
     val name: String?,
    @Json(name = "parent")
     val parent: String?,
    @Json(name = "description")
     val description: String?,
    @Json(name = "source")
     val source: String?,
    @Json(name = "viewersCurrent")
     val viewersCurrent: Int?,
    @Json(name = "coverUrl")
     val coverUrl: String?,
    @Json(name = "backgroundUrl")
     val backgroundUrl: String?,
    @Json(name = "online")
     val online: Int?,
    @Json(name = "availableAt")
     val availableAt: String?
) {


}