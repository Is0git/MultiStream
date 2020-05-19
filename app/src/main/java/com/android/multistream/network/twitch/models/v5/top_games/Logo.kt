package com.android.multistream.network.twitch.models.v5.top_games

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Logo(
    val small: String? = null,
    val template: String? = null,
    val large: String? = null,
    val medium: String? = null
) : Parcelable
