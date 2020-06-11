package com.android.multistream.network.mixer.models.mixer_channels

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Thumbnail(

	@field:Json(name="createdAt")
	val createdAt: String? = null,

	@field:Json(name="relid")
	val relid: Int? = null,

	@field:Json(name="remotePath")
	val remotePath: String? = null,

	@field:Json(name="id")
	val id: Int = 0,

	@field:Json(name="store")
	val store: String? = null,

	@field:Json(name="type")
	val type: String? = null,

	@field:Json(name="url")
	val url: String? = null,

	@field:Json(name="updatedAt")
	val updatedAt: String? = null
) : Parcelable