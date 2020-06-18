package com.android.multistream

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.Configuration
import androidx.work.WorkManager
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.MixerPlatform
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.DaggerAppComponent
import com.android.multistream.ui.main_activity.fragments.settings_fragment.SettingsLoader
import com.android.multistream.utils.WorkFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {
    companion object {
        const val ALARM_CHANNEL_ID = "100"
    }
    @Inject
    lateinit var platformManager: PlatformManager
    @Inject
    lateinit var twitchPlatform: TwitchPlatform
    @Inject
    lateinit var mixerPlatform: MixerPlatform
    @Inject
    lateinit var settingsLoader: SettingsLoader
    @Inject
    lateinit var workFactory: WorkFactory

    override fun onCreate() {
        super.onCreate()
        twitchPlatform.platformName = "Twitch"
//        mixerPlatform.platformName = "Mixer"
        platformManager.addPlatform(twitchPlatform)
//        platformManager.addPlatform(mixerPlatform)
        addCustomWorkManagerConfiguration()
        settingsLoader.apply {
            resolveLocale()
            resolveDarkMode(application.resources)
            createNotificationChannels()
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.alarm_channel)
            val descriptionText = applicationContext.getString(R.string.alarm_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(ALARM_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun addCustomWorkManagerConfiguration() {
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workFactory).build())
    }
}