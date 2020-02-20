package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.models.Token
import com.android.multistream.network.twitch.models.Validation
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface TwitchAuthService {

    @POST("helix/")
    suspend fun getAuthToken(@Header(value = "Authorization") key: String) : Response<Token>

    @POST("oauth2/validate")
    suspend fun checkValidation(@Header("Authorization") access_token: String) : Response<Validation>

}