package com.android.multistream.ui.auth_activity.fragments

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.android.multistream.R
import com.android.multistream.databinding.AuthActivityBinding
import com.android.multistream.ui.main_activity.MainActivity
import com.example.daggerviewmodelfragment.ViewModelFactory
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {

    lateinit var binding: AuthActivityBinding
    lateinit var authViewModel: AuthViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.auth_activity)
        authViewModel = ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)
            if (!intent.getBooleanExtra(
                    "isSplashScreenEnabled",
                    true
                )
            ) findNavController(R.id.nav_host_container).navigate(R.id.action_splashScreenFragment_to_introPageTwo)
    }

    fun launchMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and FLAG_ACTIVITY_NO_ANIMATION)
        }
        startActivity(intent)
        this.finish()
    }
}