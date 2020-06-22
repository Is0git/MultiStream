package com.iso.multistream.network.twitch.models.v5.top_games

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game(
    val giantbombId: Int? = null,
    val popularity: Int? = null,
    val name: String? = null,
    val logo: Logo? = null,
    val box: Box? = null,
    val _id: Int? = null,
    val locale: String? = null,
    val localizedName: String? = null
) : Parcelable
