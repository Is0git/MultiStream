package com.android.multistream.di.modules

import android.content.SharedPreferences
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.di.qualifiers.AuthPreferencesQualifier
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AuthModule {

    @JvmStatic
    @Provides
    @Singleton
    fun platformManager(@AuthPreferencesQualifier sharedPreferences: SharedPreferences, @AuthPreferencesQualifier sharedPreferencesEditor: SharedPreferences.Editor): PlatformManager {
        return PlatformManager(
            sharedPreferences,
            sharedPreferencesEditor
        )
    }
}