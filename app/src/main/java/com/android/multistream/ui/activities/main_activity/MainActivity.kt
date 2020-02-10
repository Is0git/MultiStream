package com.android.multistream.ui.activities.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.multistream.R
import com.android.multistream.databinding.ActivityMainBinding
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            MainActivityViewModel::class.java)

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
        token?.let {  mainActivityViewModel.authorize(TWITCH_TOKEN, token) }
    }


}
