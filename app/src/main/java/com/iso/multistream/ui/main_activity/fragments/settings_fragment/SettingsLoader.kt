package com.iso.multistream.ui.main_activity.fragments.settings_fragment

import android.app.Application
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import com.iso.multistream.R
import com.iso.multistream.di.qualifiers.SettingsPreferencesQualifier
import com.iso.multistream.utils.setLocale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsLoader @Inject constructor(
    var application: Application,
    @SettingsPreferencesQualifier var settingsPreference: SharedPreferences,
    @SettingsPreferencesQualifier var settingsPreferencesEditor: SharedPreferences.Editor
) {

    val resources: Resources = application.resources

    fun resolveDarkMode(resources: Resources) {
        val result = settingsPreference.getBoolean(
            application.getString(R.string.dark_mode_preference),
            false
        )
        if (result) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) else AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun resolveLocale() {
        val result = settingsPreference.getString(
            this extractFrom R.string.locale_list,
            this extractFrom R.string.en
        )
        when (result) {
            resources.getString(R.string.ru) -> setLocale("ru", resources)
            resources.getString(R.string.en) -> setLocale("en", resources)
        }
    }

    private infix fun extractFrom(@StringRes id: Int): String {
        return application.getString(id)
    }
}