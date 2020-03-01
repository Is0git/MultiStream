package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.models.auth.Token
import com.android.multistream.network.twitch.models.auth.Validation
import com.android.multistream.network.twitch.constants.CLIENT_ID
import com.android.multistream.network.twitch.constants.CLIENT_SECRET
import com.android.multistream.network.twitch.constants.REDIRECT_URI
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface TwitchAuthService {

    @POST
    @FormUrlEncoded
    suspend fun getAuthToken(
        @Field("code") code: String,
        @Field("client_id") client_id: String = CLIENT_ID,
        @Field("client_secret") clientSecret: String = CLIENT_SECRET,
        @Field("grant_type") authorization_code: String = "authorization_code",
        @Field("redirect_uri") redirectUri: String = REDIRECT_URI,
        @Url url: String = "https://id.twitch.tv/oauth2/token"
    ): Response<Token>

    @POST
    suspend fun checkValidation(@Header("Authorization") access_token: String, @Url url: String = "https://id.twitch.tv/oauth2/validate"): Response<Validation>

    @POST
    @FormUrlEncoded
    fun refreshToken(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("client_id") clientId: String = CLIENT_ID,
        @Field("client_secret") clientSecret: String = CLIENT_SECRET,
        @Field("refresh_token") refreshToken: String,
        @Url url: String = "https://id.twitch.tv/oauth2/token"
    ) : Call<Token>

}