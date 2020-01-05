package com.android.multistream.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.multistream.R
import com.android.multistream.databinding.ActivityMainBinding
import com.android.multistream.util.TWITCH_TOKEN
import com.android.multistream.util.ViewModelFactory
import com.android.multistream.util.twitchAPI.twitchAuthPage
import com.android.multistream.util.twitchAPI.uriQuery
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

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
