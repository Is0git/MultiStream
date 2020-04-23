package com.android.multistream.di.modules

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.android.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentScope
import com.android.multistream.di.qualifiers.SettingsPreferencesQualifier
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SettingsModule {
    @Provides
    @JvmStatic
    @HomeFragmentScope
    @SettingsPreferencesQualifier
    fun getSettingsPreferences(application: Application) : SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}