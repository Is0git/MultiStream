package com.android.multistream.network.mixer.models.mixer_channels

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Type(

	@Json(name="coverUrl")
	val coverUrl: String? = null,

	@Json(name="backgroundUrl")
	val backgroundUrl: String? = null,

	@Json(name="parent")
	val parent: String? = null,

	@Json(name="viewersCurrent")
	val viewersCurrent: Int? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="online")
	val online: Int? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="source")
	val source: String? = null
) : Parcelable