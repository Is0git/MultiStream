package com.android.multistream.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.android.multistream.R
import com.android.multistream.ui.main.activities.main_activity.MainActivity

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        (requireActivity() as MainActivity).hideActionBar()
        setPreferencesFromResource(R.xml.settings, rootKey)
    }



    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            getString(R.string.twitch_visibility) -> reverseBooleanValue(sharedPreferences, key)
        }
    }


    private fun reverseBooleanValue(sharedPreferences: SharedPreferences?, key: String?) {
        val editor = sharedPreferences?.edit()
        if (sharedPreferences?.getBoolean(key, false)!!) editor?.putBoolean(
            key,
            false
        ) else editor?.putBoolean(key, true)
        editor?.apply()
    }
}