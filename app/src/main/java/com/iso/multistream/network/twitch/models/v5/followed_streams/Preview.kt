package com.iso.multistream.network.twitch.models.v5.followed_streams


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Preview(

    @field:Json(name = "small")
    val small: String? = null,

    @field:Json(name = "template")
    val template: String? = null,

    @field:Json(name = "large")
    val large: String? = null,

    @field:Json(name = "medium")
    val medium: String? = null
) : Parcelable