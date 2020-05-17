package com.android.multistream.ui.splash_screen

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
import com.android.multistream.R
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.ui.main.activities.main_activity.MainActivity
import com.android.multistream.ui.main.activities.main_activity.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenFragment : Fragment(R.layout.splash_screen_layout) {

    lateinit var logoView: ImageView
    lateinit var navController: NavController
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logoView = view.findViewById(R.id.logo)
        view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorSurface, null))
        mainActivityViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        if (logoView.drawable is AnimatedVectorDrawable) {
            (logoView.drawable as AnimatedVectorDrawable).start()
        }
        navController = findNavController()
        lifecycleScope.launch {
            mainActivityViewModel.validateAccessTokens()
            if (mainActivityViewModel.isValidated(TwitchPlatform::class.java)) {
                withContext(Dispatchers.Main) {
                    navController.navigate(R.id.action_splashScreenFragment_to_main)
                    (requireActivity() as MainActivity).initNavigationDrawer()
                }
            } else withContext(Dispatchers.Main) { navController.navigate(R.id.action_splashScreenFragment_to_intro) }
        }
    }
}
