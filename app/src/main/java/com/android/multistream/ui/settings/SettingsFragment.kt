package com.android.multistream.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.android.multistream.R
import com.android.multistream.ui.main.activities.main_activity.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorSurface, null))
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        (requireActivity() as MainActivity).hideActionBar()
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}