package com.android.multistream.network.mixer.models.top_games

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MixerTopGames(
    @Json(name = "id") val id: Int?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "parent")
    var parent: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "source")
    var source: String?,
    @Json(name = "viewersCurrent")
    var viewersCurrent: Int?,
    @Json(name = "coverUrl")
    var coverUrl: String?,
    @Json(name = "backgroundUrl")
    var backgroundUrl: String?,
    @Json(name = "online")
    var online: Int?,
    @Json(name = "availableAt")
    var availableAt: String?
) : Parcelable