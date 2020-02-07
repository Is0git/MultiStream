package com.android.multistream.di

import com.android.multistream.di.MainActivity.MixerRetrofitQualifier
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.twitch.TopGamesTwitchAdapter
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.utils.mixerAPI.MIXER_URL
import com.android.multistream.utils.twitchAPI.TWITCH_URL
import com.android.multistream.utils.twitchAPI.URL
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
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
        HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }


    @Provides
    @Singleton
    @JvmStatic
    fun getOkHttpClient(interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
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
    @TwitchRetrofitQualifier
    fun getTwitchRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(TopGamesTwitchAdapter()).build()))
        .client(client)
        .baseUrl(TWITCH_URL)
        .build()

    @Provides
    @Singleton
    @JvmStatic
    @MixerRetrofitQualifier
    fun getMixerRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .baseUrl(MIXER_URL)
        .build()


//    val service = twitchRetrofit.create(TwitchService::class.java)
//    val mixerService = mixerRetrofit.create(MixerService::class.java)

    @Provides
    @Singleton
    @JvmStatic
    fun getMixerService(@MixerRetrofitQualifier retrofit: Retrofit) = retrofit.create(
        MixerService::class.java
    )

    @Provides
    @Singleton
    @JvmStatic
    fun getTwitchService(@TwitchRetrofitQualifier retrofit: Retrofit) = retrofit.create(
        TwitchService::class.java
    )

}