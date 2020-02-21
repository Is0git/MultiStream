package com.android.multistream.di.modules

import android.content.SharedPreferences
import com.android.multistream.auth.PlatformManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AuthModule {

    @JvmStatic
    @Provides
    @Singleton
    fun platformManager(sharedPreferences: SharedPreferences, sharedPreferencesEditor: SharedPreferences.Editor) : PlatformManager {
        return PlatformManager(sharedPreferences, sharedPreferencesEditor)
    }
}