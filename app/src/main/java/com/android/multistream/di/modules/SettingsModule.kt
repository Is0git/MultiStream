package com.android.multistream.di.modules

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import com.android.multistream.di.qualifiers.SettingsPreferencesQualifier
import com.android.multistream.ui.main.activities.main_activity.MainActivity
import dagger.Module
import dagger.Provides

@Module
object SettingsModule {

    @Provides
    @JvmStatic
    @MainActivityScope
    @SettingsPreferencesQualifier
    fun getSettingsPreferences(activity: MainActivity): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(activity)
    }
}