package com.android.multistream.di

import com.android.multistream.util.twitchAPI.TWITCH_URL
import com.android.multistream.util.twitchAPI.URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Provides
    @Singleton
    @JvmStatic
    fun interceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY}


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
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .baseUrl(TWITCH_URL)
        .build()
}