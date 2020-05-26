package com.android.multistream.ui.intro.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.android.multistream.R
import com.android.multistream.auth.platforms.Platform
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.databinding.IntroPageTwoBinding
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.mixer.constants.CLIENT_ID
import com.android.multistream.network.mixer.constants.MIXER_URL
import com.android.multistream.network.twitch.constants.URL
import com.android.multistream.ui.auth.fragments.LoginFragment
import com.android.multistream.ui.main_activity.MainActivityViewModel
import com.android.multistream.utils.TWITCH
import com.android.stripesliderview.slider.SlideLayout
import com.android.stripesliderview.viewpager.PageData
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject


class IntroPageTwo : DaggerFragment() {

    lateinit var binding: IntroPageTwoBinding
    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var navController: NavController

    lateinit var retrofit: Retrofit
    var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IntroPageTwoBinding.inflate(inflater, container, false)
        mainActivityViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        val isTwitchValidated = mainActivityViewModel.isValidated(TwitchPlatform::class.java)
        val pageOne = PageData.Builder()
            .setPageButtonText("SIGN IN")
            .setTitleText("TWITCH")
            .setLogoDrawableId(R.drawable.ic_twitch_logo)
            .setCircleDrawableId(R.drawable.ic_circle)
            .setUnderCircleDrawableId(R.drawable.ic_lines)
            .setLogoWidthRatio(1f)
            .setHeightRatio(0.70f)
            .setLogoOffSetRatio(0.50f)
            .setState(PageData.ProgressButtonState.IDLE)
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
//            onSkipButtonClick { navController.navigate(R.id.action_intro_to_main) }
        }
        mainActivityViewModel.statesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Platform.AuthState.Completed -> {
                    Snackbar.make(requireView(), "COMPLETED", Snackbar.LENGTH_SHORT).show()
                    binding.slideLayout.setButtonState(0, PageData.ProgressButtonState.COMPLETED)
                }
                is Platform.AuthState.Failed -> {
                    Snackbar.make(requireView(), "FAILED", Snackbar.LENGTH_SHORT).show()
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
        val newFragment: DialogFragment = LoginFragment().apply {
            arguments = Bundle().also {
                it.putInt("platform", platform)
                it.putInt("position", position)
            }
        }
        newFragment.dialog?.window?.setLayout(MATCH_PARENT, MATCH_PARENT)
        newFragment.showNow(fragmentManager, platform.toString())
    }

    fun resetStates(position: Int) {
        binding.slideLayout.setButtonState(position, PageData.ProgressButtonState.FAILED)
    }

}