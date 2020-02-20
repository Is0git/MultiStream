package com.android.multistream.di.MainActivity

import com.android.multistream.auth.PlatformManager
import com.android.multistream.auth.Platforms.TwitchPlatform
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AuthModule {

    @JvmStatic
    @Provides
    @Singleton
    fun platformManager(platformManager: PlatformManager, twitchPlatform: TwitchPlatform) : PlatformManager {
        platformManager.addPlatform(twitchPlatform)
        return platformManager
    }
}