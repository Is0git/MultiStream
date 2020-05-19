package com.android.multistream.network.twitch.models.v5.top_games

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopItem(
    val game: Game? = null,
    val viewers: Int? = null,
    val channels: Int? = null
) : Parcelable
