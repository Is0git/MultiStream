package com.android.multistream.ui.intro.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.android.multistream.R
import com.android.multistream.auth.Platform
import com.android.multistream.auth.Platforms.TwitchPlatform
import com.android.multistream.databinding.IntroPageTwoBinding
import com.android.multistream.network.twitch.constants.TWITCH_AUTH_PAGE
import com.android.multistream.ui.main.activities.main_activity.MainActivityViewModel
import com.android.stripesliderview.listeners.OnProgressButtonListener
import com.android.stripesliderview.slider.SlideLayout
import com.android.stripesliderview.viewpager.PageData
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IntroPageTwo : DaggerFragment(), OnProgressButtonListener{
    lateinit var binding: IntroPageTwoBinding

    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = IntroPageTwoBinding.inflate(inflater, container, false)

        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        val listPage = listOf(
            PageData(
                "SIGN",
                "SYNC TWITCH",
                R.drawable.ic_twitch_logo,
                R.drawable.ic_circle,
                R.drawable.ic_lines,
                1f,
                0.70f,
                0.50f) {findNavController().navigate(R.id.loginFragment)},
            PageData(
                "SIGN IN",
                "SYNC MIXER",
                R.drawable.mixer_logo,
                R.drawable.ic_circle,
                R.drawable.ic_lines,
                1f,
                0.30f,
                0.85f
            ) {findNavController().navigate(R.id.loginFragment)}
        )

        (binding.root as SlideLayout).apply {
            setOnProgressButtonListener(this@IntroPageTwo)
            viewPagerAdapter.addPages(listPage)
            onSkipButtonClick { findNavController().navigate(R.id.action_global_main) }
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

    override fun onClick(view: br.com.simplepass.loadingbutton.customViews.CircularProgressButton) {
        lifecycleScope.launch {
            delay(1500)
            (binding.root as SlideLayout).getCurrentPageData().onSyncClick()
        }
    }
}