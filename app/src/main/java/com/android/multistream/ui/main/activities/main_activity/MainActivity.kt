package com.android.multistream.ui.main.activities.main_activity

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.multistream.R
import com.android.multistream.databinding.ActivityMainBinding
import com.android.multistream.ui.player.fragments.PlayerFragment
import com.android.multistream.utils.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var navController: NavController

    lateinit var mainActivityViewModel: MainActivityViewModel
    val transition by lazy {
        TransitionInflater.from(this)
            .inflateTransition(R.transition.games_list_expand_transition)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityViewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        navController = findNavController(R.id.main_fragment_container)

        binding.apply {
            bottomNav.setupWithNavController(navController)
            motionLayout.setDefaultTransitionHandler(supportFragmentManager)
        }

        setListeners()


    }



    private fun setListeners() {
        binding.settingsIcon.setOnClickListener { navController.navigate(R.id.action_global_settingsFragment) }
    }

    fun hideActionBar() {
        binding.apply {
            bgImage.visibility = View.GONE
            menuDrawerIcon.visibility = View.GONE
            settingsIcon.visibility = View.GONE
            bottomNav.visibility = View.GONE
        }
    }

    fun showActionBar() {
            binding.apply {
                bgImage.visibility = View.VISIBLE
                menuDrawerIcon.visibility = View.VISIBLE
                settingsIcon.visibility = View.VISIBLE
                bottomNav.visibility = View.VISIBLE
            }
    }

    fun createPlayerFragment(channelTitle: String?, channelName: String?, channelImage: String?, channelCategory: String?, channelDisplayName: String?) {
        val bundle = Bundle().apply {
            putString("channel_title", channelTitle)
            putString("channel_name", channelName)
            putString("channel_image", channelImage)
            putString("channel_category", channelCategory)
            putString("channel_display_name", channelDisplayName)
        }
        supportFragmentManager.beginTransaction().replace(R.id.player_fragment, PlayerFragment::class.java, bundle, "player_fragment").commit()
    }
}
