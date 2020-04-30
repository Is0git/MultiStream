package com.android.multistream.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.android.multistream.R
import com.android.multistream.ui.main.activities.main_activity.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        (requireActivity() as MainActivity).hideActionBar()
        setPreferencesFromResource(R.xml.settings, rootKey)
    }



    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

}