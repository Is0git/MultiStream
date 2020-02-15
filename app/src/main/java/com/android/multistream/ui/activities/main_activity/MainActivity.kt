package com.android.multistream.ui.activities.main_activity

import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.multistream.R
import com.android.multistream.databinding.ActivityMainBinding
import com.android.multistream.utils.ScreenUnit
import com.android.multistream.utils.TWITCH_TOKEN
import com.android.multistream.utils.ViewModelFactory
import com.android.multistream.utils.twitchAPI.uriQuery
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var mainActivityViewModel: MainActivityViewModel

    val transition by lazy {
        TransitionInflater.from(this)
            .inflateTransition(R.transition.games_list_expand_transition)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            MainActivityViewModel::class.java
        )
        binding.settingsIcon.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root as ViewGroup, transition)

            if (binding.navigation.isVisible)  {
                binding.navigation.translationX = -350f

            }
            else {
                binding.navigation.layoutParams = ConstraintLayout.LayoutParams(ScreenUnit.convertDpToPixel(200f), MATCH_PARENT)
                binding.navigation.visibility = View.VISIBLE
            }


        }
//        binding.button.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW, twitchAuthPage.toUri())
//            startActivity(intent)
//        }
        binding.bottomNav.setupWithNavController(findNavController(R.id.main_fragment_container))
//        binding.token.setOnClickListener { binding.textID.text = mainActivityViewModel.getToken(TWITCH_TOKEN) }
    }

    override fun onResume() {
        super.onResume()
        authorizeTwitch()
    }

    private fun authorizeTwitch() {
        val token: String? = intent.data?.let { uriQuery(it.toString()) }
        token?.let { mainActivityViewModel.authorize(TWITCH_TOKEN, token) }
    }


}
