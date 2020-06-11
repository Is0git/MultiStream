package com.android.multistream.network.mixer.models.mixer_channels

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Type(

	@field:Json(name="coverUrl")
	val coverUrl: String? = null,

	@field:Json(name="backgroundUrl")
	val backgroundUrl: String? = null,

	@field:Json(name="parent")
	val parent: String? = null,

	@field:Json(name="viewersCurrent")
	val viewersCurrent: Int? = null,

	@field:Json(name="name")
	val name: String? = null,

	@field:Json(name="online")
	val online: Int? = null,

	@field:Json(name="id")
	val id: Int? = null,

	@field:Json(name="source")
	val source: String? = null
) : Parcelable