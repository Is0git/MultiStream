package com.android.multistream

import com.android.multistream.auth.PlatformManager
import com.android.multistream.auth.platforms.MixerPlatform
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    lateinit var platformManager: PlatformManager
    @Inject
    lateinit var twitchPlatform: TwitchPlatform

    @Inject
    lateinit var mixerPlatform: MixerPlatform

    override fun onCreate() {
        super.onCreate()
        twitchPlatform.platformName = "Twitch"
        platformManager.addPlatform(twitchPlatform)
        platformManager.addPlatform(mixerPlatform)
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       return DaggerAppComponent.builder().application(this).build()
    }
}