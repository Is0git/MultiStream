package com.android.multistream.network.mixer.models.top_games

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MixerTopGames(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "name")
    var name: String? = null,
    @field:Json(name = "parent")
    var parent: String? = null,
    @field:Json(name = "description")
    var description: String? = null,
    @field:Json(name = "source")
    var source: String? = null,
    @field:Json(name = "viewersCurrent")
    var viewersCurrent: Int? = null,
    @field:Json(name = "coverUrl")
    var coverUrl: String? = null,
    @field:Json(name = "backgroundUrl")
    var backgroundUrl: String? = null,
    @field:Json(name = "online")
    var online: Int? = null,
    @field:Json(name = "availableAt")
    var availableAt: String? = null
) : Parcelable {
    companion object {
        fun buildMixerTopGames(
            gameId: Int?,
            name: String?,
            gameImage: String?,
            channels: Int?,
            viewers: Int?
        ): MixerTopGames {
            return MixerTopGames(gameId, name, backgroundUrl = gameImage)
        }
    }
}