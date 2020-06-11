package com.android.multistream.ui.auth_activity.fragments.intro

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.android.multistream.R
import com.android.multistream.auth.platforms.Platform
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.databinding.IntroPageTwoBinding
import com.android.multistream.di.qualifiers.SettingsPreferencesQualifier
import com.android.multistream.ui.auth_activity.fragments.AuthActivity
import com.android.multistream.ui.auth_activity.fragments.AuthViewModel
import com.android.multistream.ui.auth_activity.fragments.login.LoginFragment
import com.android.multistream.ui.main_activity.MainActivityViewModel
import com.android.multistream.utils.TWITCH
import com.android.stripesliderview.slider.SlideLayout
import com.android.stripesliderview.viewpager.PageData
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import retrofit2.Retrofit
import javax.inject.Inject


class IntroPageFragment : DaggerFragment() {

    lateinit var binding: IntroPageTwoBinding
    lateinit var authViewModel: AuthViewModel
    lateinit var navController: NavController
    @Inject @SettingsPreferencesQualifier
    lateinit var settingsPreferencesEditor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IntroPageTwoBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        val isTwitchValidated = authViewModel.isValidated(TwitchPlatform::class.java)
        val pageOne = PageData.Builder()
            .setPageButtonText("SIGN IN")
            .setTitleText("TWITCH")
            .setLogoDrawableId(R.drawable.ic_twitch_logo)
            .setCircleDrawableId(R.drawable.ic_circle)
            .setUnderCircleDrawableId(R.drawable.ic_lines)
            .setLogoWidthRatio(1f)
            .setHeightRatio(0.70f)
            .setLogoOffSetRatio(0.50f)
            .setState(if (isTwitchValidated) PageData.ProgressButtonState.COMPLETED else PageData.ProgressButtonState.IDLE)
            .setOnSyncButtonClickListener {
                createDialog(TWITCH, 0)

            }
            .build()
        val pageTwo = PageData.Builder()
            .setPageButtonText("SIGN IN")
            .setTitleText("MIXER")
            .setLogoDrawableId(R.drawable.mixer_logo)
            .setCircleDrawableId(R.drawable.ic_circle)
            .setUnderCircleDrawableId(R.drawable.ic_lines)
            .setLogoWidthRatio(1f)
            .setHeightRatio(0.30f)
            .setLogoOffSetRatio(0.85f)
            .setState(PageData.ProgressButtonState.IDLE)
            .setOnSyncButtonClickListener {
                Snackbar.make(requireView(), "MIXER IS AUTH IS DISABLED", Snackbar.LENGTH_SHORT).show()
                binding.slideLayout.setButtonState(1, PageData.ProgressButtonState.FAILED)
            }
            .build()
        val pageList = listOf(pageOne, pageTwo)
        (binding.root as SlideLayout).apply {
            viewPagerAdapter.addPages(pageList)
            onSkipButtonClick { (requireActivity() as AuthActivity).launchMainActivity() }
        }
        authViewModel.statesLiveData.observe(viewLifecycleOwner) {
            val platformKeyName = getString(R.string.twitch_sync)
            when (it) {
                is Platform.AuthState.Completed -> {
                    settingsPreferencesEditor.putBoolean(platformKeyName, true).apply()
                    binding.slideLayout.setButtonState(0, PageData.ProgressButtonState.COMPLETED)
                }
                is Platform.AuthState.Failed -> {
                    Snackbar.make(requireView(), "FAILED", Snackbar.LENGTH_SHORT).show()
                    settingsPreferencesEditor.putBoolean(platformKeyName, false).apply()
                    binding.slideLayout.setButtonState(0, PageData.ProgressButtonState.FAILED)
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = findNavController()
    }

    private fun createDialog(platform: Int, position: Int) {
        val fragmentManager = requireFragmentManager()
        fragmentManager.findFragmentByTag(platform.toString())?.also {
            fragmentManager.beginTransaction().remove(it)
        }
        val newFragment: DialogFragment = LoginFragment()
            .apply {
            arguments = Bundle().also {
                it.putInt("platform", platform)
                it.putInt("position", position)
            }
        }
        newFragment.dialog?.window?.setLayout(MATCH_PARENT, MATCH_PARENT)
        newFragment.showNow(fragmentManager, platform.toString())
    }

}