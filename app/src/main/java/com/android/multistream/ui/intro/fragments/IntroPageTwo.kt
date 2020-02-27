package com.android.multistream.ui.intro.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.android.multistream.R
import com.android.multistream.auth.Platform
import com.android.multistream.databinding.IntroPageTwoBinding
import com.android.multistream.ui.main.activities.main_activity.MainActivityViewModel
import com.android.multistream.network.twitch.constants.twitchAuthPage
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

        mainActivityViewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)



        binding = IntroPageTwoBinding.inflate(inflater, container, false)


        val listPage = listOf(
            PageData(
                "DO SOMETHING",
                "HELLO",
                R.drawable.ic_twitch_logo,
                R.drawable.ic_circle,
                R.drawable.ic_lines,
                1f,
                0.70f,
                0.50f
            ),
            PageData(
                "DO SOMETHING",
                "HELLO",
                R.drawable.mixer_logo,
                R.drawable.ic_circle,
                R.drawable.ic_lines,
                0.80f,
                0.30f,
                0.90f
            )
        )

        (binding.root as SlideLayout).viewPagerAdapter.addPages(listPage)
//        binding.button.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW, twitchAuthPage.toUri())
//            startActivity(intent)
//        }
//        mainActivityViewModel.statesLiveData.observe(viewLifecycleOwner) {
//            when(it) {
//                is Platform.AuthState.Completed -> {
//                    binding.progressBar.visibility = View.INVISIBLE
//                    Toast.makeText(activity!!.applicationContext, "COMPLETED", Toast.LENGTH_LONG).show()
//                }
//                is Platform.AuthState.Failed -> {
//                    binding.progressBar.visibility = View.INVISIBLE
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