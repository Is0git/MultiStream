package com.iso.multistream.ui.auth_activity.fragments.splash_screen

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.iso.multistream.R
import com.iso.multistream.auth.platforms.TwitchPlatform
import com.iso.multistream.ui.auth_activity.fragments.AuthActivity
import com.iso.multistream.ui.auth_activity.fragments.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashScreenFragment : Fragment(R.layout.splash_screen_layout) {

    lateinit var logoView: ImageView
    lateinit var navController: NavController
    lateinit var authViewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logoView = view.findViewById(R.id.logo)
        view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorSurface, null))
        authViewModel =
            ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        if (logoView.drawable is AnimatedVectorDrawable) {
            (logoView.drawable as AnimatedVectorDrawable).start()
        }
        navController = findNavController()
        lifecycleScope.launch {
            authViewModel.validateAccessTokens()
            if (authViewModel.isValidated(TwitchPlatform::class.java)) {
                withContext(Dispatchers.Main) { (requireActivity() as AuthActivity).launchMainActivity() }
            } else withContext(Dispatchers.Main) { navController.navigate(R.id.action_splashScreenFragment_to_introPageTwo) }
        }
    }
}
