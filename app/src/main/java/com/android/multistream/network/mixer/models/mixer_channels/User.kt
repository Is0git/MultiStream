package com.android.multistream.network.mixer.models.mixer_channels

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

	@field:Json(name="sparks")
	val sparks: Int? = null,

	@field:Json(name="level")
	val level: Int? = null,


	@field:Json(name="avatarUrl")
	val avatarUrl: String? = null,

	@field:Json(name="verified")
	val verified: Boolean? = null,

	@field:Json(name="bio")
	val bio: String? = null,

	@field:Json(name="experience")
	val experience: Int? = null,

	@field:Json(name="createdAt")
	val createdAt: String? = null,

	@field:Json(name="id")
	val id: Int? = null,

	@field:Json(name="username")
	val username: String? = null,

	@field:Json(name="updatedAt")
	val updatedAt: String? = null
) : Parcelable