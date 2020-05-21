package com.android.multistream.network.mixer.models.mixer_channels

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

	@Json(name="sparks")
	val sparks: Int? = null,

	@Json(name="level")
	val level: Int? = null,


	@Json(name="avatarUrl")
	val avatarUrl: String? = null,

	@Json(name="verified")
	val verified: Boolean? = null,

	@Json(name="bio")
	val bio: String? = null,

	@Json(name="experience")
	val experience: Int? = null,

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="username")
	val username: String? = null,

	@Json(name="updatedAt")
	val updatedAt: String? = null
) : Parcelable