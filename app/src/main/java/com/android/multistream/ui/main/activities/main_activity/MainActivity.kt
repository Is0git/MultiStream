package com.android.multistream.ui.main.activities.main_activity

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
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

        binding.bottomNav.setupWithNavController(navController)

        setListeners()

        hideActionBar()
    }



    fun setListeners() {
        binding.settingsIcon.setOnClickListener { navController.navigate(R.id.action_global_settingsFragment) }
    }

    fun hideActionBar() {
        binding.apply {
            bgImage.visibility = View.INVISIBLE
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

    fun createPlayerFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.player_fragment_container, PlayerFragment().apply {
        }).commit()
    }
}
