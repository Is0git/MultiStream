package com.iso.multistream.ui.main_activity.fragments.settings_fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.iso.multistream.R
import com.iso.multistream.auth.platform_manager.PlatformManager
import com.iso.multistream.auth.platforms.TwitchPlatform
import com.iso.multistream.ui.auth_activity.fragments.AuthActivity
import com.iso.multistream.utils.setLocale
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener,
    HasAndroidInjector, SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any?>
    @Inject
    lateinit var platformManager: PlatformManager

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any?>? {
        return androidInjector
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorSurface, null))
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        preferenceManager.apply {
            findPreference<SwitchPreference>(getString(R.string.twitch_sync))?.onPreferenceChangeListener =
                this@SettingsFragment
            findPreference<SwitchPreference>(getString(R.string.dark_mode_preference))?.onPreferenceChangeListener =
                this@SettingsFragment
            findPreference<ListPreference>(getString(R.string.locale_list))?.onPreferenceChangeListener =
                this@SettingsFragment
        }
    }


    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        when (preference?.key) {
            getString(R.string.twitch_sync) -> {
                if (!(preference as SwitchPreference).isChecked) startAuthActivity() else {
                    platformManager.invalidateToken(TwitchPlatform::class.java)
                    return true
                }
            }
            getString(R.string.dark_mode_preference) -> {
                if (!(preference as SwitchPreference).isChecked) AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                ) else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                return true
            }
            getString(R.string.locale_list) -> {
                (preference as ListPreference).apply {
                    if (newValue == preference.value) return true
                    when (newValue) {
                        getString(R.string.ru) -> setLocale("ru", resources)
                        getString(R.string.en) -> setLocale("en", resources)
                    }
                    activity?.recreate()
                    return true
                }
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        preferenceManager?.apply {
            val preferencesKey = getString(R.string.twitch_sync)
            val preference  =  preferenceScreen.findPreference<SwitchPreference>(preferencesKey)
            val trueValue = sharedPreferences.getBoolean(preferencesKey, false)
            if (preference?.isChecked != trueValue) {
                preference?.isChecked = trueValue
                requireActivity().recreate()
            }

        }
    }
    private fun startAuthActivity() {
        val intent = Intent(requireContext(), AuthActivity::class.java).apply {
            putExtra("isSplashScreenEnabled", false)
        }
        startActivity(intent)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key) {
            getString(R.string.twitch_sync) -> {
               if (!sharedPreferences?.getBoolean(key, false)!!) {
                   requireActivity().recreate()
               }
            }
            getString(R.string.twitch_sync), getString(R.string.twitch_visibility), getString(R.string.mixer_visibility) ->  {
                requireActivity().recreate()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}
