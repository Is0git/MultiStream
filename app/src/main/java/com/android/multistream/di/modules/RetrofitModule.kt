package com.android.multistream.di.modules

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.qualifiers.MixerQualifier
import com.android.multistream.di.qualifiers.TwitchAuthQualifier
import com.android.multistream.di.qualifiers.TwitchQualifier
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.mixer.adapters.ChannelSearchesAdapter
import com.android.multistream.network.mixer.adapters.MixerAdapter
import com.android.multistream.network.mixer.constants.MIXER_URL
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.adapters.GameSearchesAdapter
import com.android.multistream.network.twitch.adapters.StreamSearchesAdapter
import com.android.multistream.network.twitch.adapters.TopGamesTwitchAdapter
import com.android.multistream.network.twitch.constants.TWITCH_URL
import com.android.multistream.network.twitch.constants.URL
import com.android.multistream.utils.ConnectivityHelper
import com.android.multistream.utils.NoInternetException
import com.squareup.moshi.KotlinJsonAdapterFactory
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
        HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }


    @Provides
    @Singleton
    @JvmStatic
    fun getConnectivityManager(application: Application) : ConnectivityManager {
        return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    @JvmStatic
    @TwitchQualifier
    fun getTwitchOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        platformManager: PlatformManager,
        connectivityManager: ConnectivityManager
    ) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .authenticator(object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                return try {
                    val authToken =
                        platformManager.getPlatform(TwitchPlatform::class.java).refreshToken()
                    response.request.newBuilder().header(
                        "Authorization",
                        if (response.request.header("Authorization")?.contains("OAuth")!!) "OAuth $authToken" else "Bearer $authToken"
                    )
                        .build()
                } catch (ex: Exception) {
                    null
                }
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
    @TwitchAuthQualifier
    fun twitchAuthOkHttpClient(interceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    @TwitchAuthQualifier
    fun getRetrofit(@TwitchAuthQualifier client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .baseUrl(URL)
        .build()


    @Provides
    @Singleton
    @JvmStatic
    @TwitchQualifier
    fun getTwitchRetrofit(@TwitchQualifier client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(TopGamesTwitchAdapter())
                    .add(StreamSearchesAdapter())
                    .add(GameSearchesAdapter())
                    .build()
            )
        )
        .client(client)
        .baseUrl(TWITCH_URL)
        .build()

    @Provides
    @Singleton
    @JvmStatic
    @MixerQualifier
    fun getMixerRetrofit(@MixerQualifier client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(ChannelSearchesAdapter())
                    .add(MixerAdapter())
                    .build()
            )
        )
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