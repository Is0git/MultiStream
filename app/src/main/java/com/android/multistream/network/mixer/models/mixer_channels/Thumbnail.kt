package com.android.multistream.network.mixer.models.mixer_channels

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Thumbnail(

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="relid")
	val relid: Int? = null,

	@Json(name="remotePath")
	val remotePath: String? = null,

	@Json(name="id")
	val id: Int = 0,

	@Json(name="store")
	val store: String? = null,

	@Json(name="type")
	val type: String? = null,

	@Json(name="url")
	val url: String? = null,

	@Json(name="updatedAt")
	val updatedAt: String? = null
) : Parcelable