package com.android.multistream.di.modules

import com.android.multistream.auth.PlatformManager
import com.android.multistream.auth.Platforms.TwitchPlatform
import com.android.multistream.di.qualifiers.MixerQualifier
import com.android.multistream.di.qualifiers.TwitchQualifier
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.twitch.TopGamesTwitchAdapter
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.utils.mixerAPI.MIXER_URL
import com.android.multistream.utils.twitchAPI.TWITCH_URL
import com.android.multistream.utils.twitchAPI.URL
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Provides
    @Singleton
    @JvmStatic
    fun interceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY
        }




    @Provides
    @Singleton
    @JvmStatic
    @TwitchQualifier
    fun getTwitchOkHttpClient(interceptor: HttpLoggingInterceptor, platformManager: PlatformManager) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .authenticator(object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
               val refreshToken = platformManager.getPlatform(TwitchPlatform::class.java).refreshToken()
              return  response.request.newBuilder().header("Authorization", refreshToken ?: "null").build()
            }

        })
        .connectTimeout(100, TimeUnit.SECONDS)
        .writeTimeout(100, TimeUnit.SECONDS)
        .readTimeout(300, TimeUnit.SECONDS)
        .build()


    @Provides
    @Singleton
    @JvmStatic
    @MixerQualifier
    fun getMixerOkHttpClient(interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(100, TimeUnit.SECONDS)
        .writeTimeout(100, TimeUnit.SECONDS)
        .readTimeout(300, TimeUnit.SECONDS)
        .build()


    @Provides
    @Singleton
    @JvmStatic
    fun getRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .baseUrl(URL)
        .build()


    @Provides
    @Singleton
    @JvmStatic
    @TwitchQualifier
    fun getTwitchRetrofit(@TwitchQualifier client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(TopGamesTwitchAdapter()).build()))
        .client(client)
        .baseUrl(TWITCH_URL)
        .build()

    @Provides
    @Singleton
    @JvmStatic
    @MixerQualifier
    fun getMixerRetrofit(@MixerQualifier client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .baseUrl(MIXER_URL)
        .build()


    @Provides
    @Singleton
    @JvmStatic
    fun getMixerService(@MixerQualifier retrofit: Retrofit) = retrofit.create(
        MixerService::class.java
    )

    @Provides
    @Singleton
    @JvmStatic
    fun getTwitchService(@TwitchQualifier retrofit: Retrofit) = retrofit.create(
        TwitchService::class.java
    )

}