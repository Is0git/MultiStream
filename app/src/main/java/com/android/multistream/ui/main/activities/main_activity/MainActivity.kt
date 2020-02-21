package com.android.multistream.ui.main.activities.main_activity

import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.multistream.R
import com.android.multistream.auth.PlatformManager
import com.android.multistream.auth.Platforms.TwitchPlatform
import com.android.multistream.databinding.ActivityMainBinding
import com.android.multistream.auth.TWITCH_TOKEN
import com.android.multistream.di.qualifiers.CustomPlatformManager
import com.android.multistream.network.twitch.models.Token
import com.android.multistream.utils.ViewModelFactory
import com.android.multistream.utils.twitchAPI.uriQuery
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var platformManager: PlatformManager
    lateinit var mainActivityViewModel: MainActivityViewModel
    var counter = 0
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

        }
        binding.bottomNav.setupWithNavController(findNavController(R.id.main_fragment_container))
//        binding.token.setOnClickListener { binding.textID.text = mainActivityViewModel.getToken(TWITCH_TOKEN) }
    }

    override fun onResume() {
        super.onResume()
        if (intent.data != null) authorizeTwitch()

    }

    fun hideActionBar() {
        binding.apply {
            bgImage.visibility = View.INVISIBLE
            menuDrawerIcon.visibility = View.GONE
            settingsIcon.visibility = View.GONE
            bottomNav.visibility = View.GONE
        }
    }

    private fun authorizeTwitch() {
        if (counter > 1) return
        counter++
        val token: String? = intent.data?.let { uriQuery(it.toString()) }
        token?.let { platformManager.getPlatform(TwitchPlatform::class.java)
          .saveAccessTokenBearer(uriQuery(it), Token::class.java) }
    }


}
