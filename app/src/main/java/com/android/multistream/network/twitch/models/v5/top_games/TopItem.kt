package com.android.multistream.network.twitch.models.v5.top_games

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopItem(
    val game: Game? = null,
    val viewers: Int? = null,
    val channels: Int? = null
) : Parcelable {
    companion object {
        fun createTopItem(
            gameId: Int?,
            name: String?,
            gameImage: String?,
            channels: Int?,
            viewers: Int?
        ): TopItem {
            val box = Box(null, null, gameImage, null)
            val game = Game(null, null, name, _id = gameId, box = box)
            return TopItem(game, viewers, channels)
        }
    }
}
