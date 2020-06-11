package com.android.multistream.network.mixer.models.mixer_channels

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MixerGameChannel(

    @field:Json(name = "featured")
    val featured: Boolean? = null,

    @field:Json(name = "viewersTotal")
    val viewersTotal: Int? = null,

    @field:Json(name = "viewersCurrent")
    val viewersCurrent: Int = 0,

    @field:Json(name = "badgeId")
    val badgeId: Int? = null,

    @field:Json(name = "hasTranscodes")
    val hasTranscodes: Boolean? = null,

    @field:Json(name = "interactive")
    val interactive: Boolean? = null,

    @field:Json(name = "bannerUrl")
    val bannerUrl: String? = null,

    @field:Json(name = "description")
    val description: String? = null,

    @field:Json(name = "type")
    val type: Type? = null,

    @field:Json(name = "createdAt")
    val createdAt: String? = null,

    @field:Json(name = "coverId")
    val coverId: Int? = null,

    @field:Json(name = "hasVod")
    val hasVod: Boolean? = null,

    @field:Json(name = "transcodingProfileId")
    val transcodingProfileId: Int? = null,

    @field:Json(name = "numFollowers")
    val numFollowers: Int? = null,

    @field:Json(name = "thumbnailId")
    val thumbnailId: Int? = null,

    @field:Json(name = "id")
    val id: Int? = null,

    @field:Json(name = "partnered")
    val partnered: Boolean? = null,

    @field:Json(name = "ftl")
    val ftl: Int? = null,

    @field:Json(name = "updatedAt")
    val updatedAt: String? = null,

    @field:Json(name = "audience")
    val audience: String? = null,

    @field:Json(name = "thumbnail")
    val thumbnail: Thumbnail? = null,

    @field:Json(name = "languageId")
    val languageId: String? = null,

    @field:Json(name = "userId")
    val userId: Int? = null,

    @field:Json(name = "featureLevel")
    val featureLevel: Int? = null,

    @field:Json(name = "suspended")
    val suspended: Boolean? = null,

    @field:Json(name = "token")
    val token: String? = null,

    @field:Json(name = "vodsEnabled")
    val vodsEnabled: Boolean? = null,

    @field:Json(name = "name")
    val name: String? = null,

    @field:Json(name = "online")
    val online: Boolean? = null,

    @field:Json(name = "typeId")
    val typeId: Int = 0,

    @field:Json(name = "user")
    val user: User? = null
) : Parcelable {
    companion object {
        fun buildMixerGame(
            userId: Int?,
            name: String?,
            logo: String?
        ) : MixerGameChannel {
			return MixerGameChannel(id =userId, name = name, bannerUrl = logo)
		}
    }
}