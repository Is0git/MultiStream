package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.models.Token
import com.android.multistream.network.twitch.models.Validation
import com.android.multistream.utils.twitchAPI.CLIENT_ID
import com.android.multistream.utils.twitchAPI.CLIENT_SECRET
import com.android.multistream.utils.twitchAPI.REDIRECT_URI
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

    @POST("oauth2/validate")
    suspend fun checkValidation(@Header("Authorization") access_token: String): Response<Validation>

}