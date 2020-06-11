package com.android.multistream.network.twitch.models.v5.followed_streams


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class StreamsItem(

    @field:Json(name = "preview")
    val preview: Preview? = null,

    @field:Json(name = "is_playlist")
    val isPlaylist: Boolean? = null,

    @field:Json(name = "video_height")
    val videoHeight: Int? = null,

    @field:Json(name = "game")
    val game: String? = null,

    @field:Json(name = "viewers")
    val viewers: Int? = null,

    @field:Json(name = "delay")
    val delay: Int? = null,

    @field:Json(name = "average_fps")
    val averageFps: Int? = null,

    @field:Json(name = "channel")
    val channel: Channel? = null,

    @field:Json(name = "created_at")
    val createdAt: String? = null,

    @field:Json(name = "_id")
    val _id: Long? = null
) : Parcelable {
    companion object {
        fun buildStreamItem(
            userId: Int?,
            name: String?,
            logo: String?
        ) : StreamsItem{
            val channel = Channel(display_name = name, logo = logo, _id = userId)
            return  StreamsItem(channel = channel)
        }
    }
}