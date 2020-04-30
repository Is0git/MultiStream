package com.android.multistream.ui.intro.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.android.multistream.R
import com.android.multistream.auth.Platform
import com.android.multistream.databinding.IntroPageTwoBinding
import com.android.multistream.ui.main.activities.main_activity.MainActivityViewModel
import com.android.stripesliderview.slider.SlideLayout
import com.android.stripesliderview.viewpager.PageData
import dagger.android.support.DaggerFragment

class IntroPageTwo : DaggerFragment(){
    lateinit var binding: IntroPageTwoBinding

    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = IntroPageTwoBinding.inflate(inflater, container, false)

        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)


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
            .setOnSyncButtonClickListener { findNavController().navigate(R.id.loginFragment) }
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
            .setState(PageData.ProgressButtonState.STARTED)
            .setOnSyncButtonClickListener { Log.d("AUTH", "MIXER CLICK") }
            .build()

        val pageList = listOf(pageOne, pageTwo)


        (binding.root as SlideLayout).apply {
            viewPagerAdapter.addPages(pageList)
            onSkipButtonClick { findNavController().navigate(R.id.action_intro_to_main) }
        }

        mainActivityViewModel.statesLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is Platform.AuthState.Completed -> {
                    Toast.makeText(requireActivity().applicationContext, "COMPLETED", Toast.LENGTH_LONG).show()

                }
                is Platform.AuthState.Failed -> {

                    Toast.makeText(requireActivity().applicationContext, "FAILED", Toast.LENGTH_LONG).show()
                }
            }
        }
        return binding.root
    }

}