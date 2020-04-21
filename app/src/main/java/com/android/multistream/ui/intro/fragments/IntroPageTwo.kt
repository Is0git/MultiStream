package com.android.multistream.ui.intro.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.android.multistream.R
import com.android.multistream.auth.Platform
import com.android.multistream.databinding.IntroPageTwoBinding
import com.android.multistream.network.twitch.constants.twitchAuthPage
import com.android.multistream.ui.main.activities.main_activity.MainActivity
import com.android.multistream.ui.main.activities.main_activity.MainActivityViewModel
import com.android.stripesliderview.slider.SlideLayout
import com.android.stripesliderview.viewpager.PageData
import dagger.android.support.DaggerFragment

class IntroPageTwo : DaggerFragment(){
    lateinit var binding: IntroPageTwoBinding

    lateinit var mainActivityViewModel: MainActivityViewModel

    val twitchIntent: Intent by lazy { Intent(Intent.ACTION_VIEW, twitchAuthPage.toUri()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)



        binding = IntroPageTwoBinding.inflate(inflater, container, false)


        val listPage = listOf(
            PageData(
                "SIGN",
                "HELLO",
                R.drawable.ic_twitch_logo,
                R.drawable.ic_circle,
                R.drawable.ic_lines,
                1f,
                0.70f,
                0.50f),
            PageData(
                "SIGN IN",
                "Hello",
                R.drawable.mixer_logo,
                R.drawable.ic_circle,
                R.drawable.ic_lines,
                1f,
                0.30f,
                0.85f
            )
        )

        (binding.root as SlideLayout).apply {
            viewPagerAdapter.addPages(listPage)
            onSkipButtonClick { findNavController().navigate(R.id.action_global_main) }

        }


//        mainActivityViewModel.statesLiveData.observe(viewLifecycleOwner) {
//            when(it) {
//                is Platform.AuthState.Completed -> {
//                    Toast.makeText(activity!!.applicationContext, "COMPLETED", Toast.LENGTH_LONG).show()
//                }
//                is Platform.AuthState.Failed -> {
//
//                    Toast.makeText(activity!!.applicationContext, "FAILED", Toast.LENGTH_LONG).show()
//                }
//            }
//        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.intent?.data.also { mainActivityViewModel.getAndSaveToken(it) }

    }
}