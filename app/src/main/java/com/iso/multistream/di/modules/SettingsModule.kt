package com.iso.multistream.di.modules

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.iso.multistream.di.qualifiers.SettingsPreferencesQualifier
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SettingsModule {

    @Provides
    @JvmStatic
    @Singleton
    @SettingsPreferencesQualifier
    fun getSettingsPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @JvmStatic
    @Singleton
    @SettingsPreferencesQualifier
    fun getSettingsPreferencesEditor(@SettingsPreferencesQualifier settingsPreferences: SharedPreferences) : SharedPreferences.Editor {
        return settingsPreferences.edit()
    }
}