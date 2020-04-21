package com.android.multistream.ui.splash_screen

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.android.multistream.R
import com.android.multistream.ui.main.activities.main_activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.measureTime

class SplashScreenFragment : Fragment(R.layout.splash_screen_layout) {
    lateinit var logoView: ImageView
    lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logoView = view.findViewById(R.id.logo)
        view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorSurface, null))

        if (logoView.drawable is AnimatedVectorDrawable) {
            (logoView.drawable as AnimatedVectorDrawable).start()
        }

        navController = findNavController()

        lifecycleScope.launch {
            delay(4000)
            withContext(Dispatchers.Main) {navController.navigate(R.id.action_splashScreenFragment_to_intro)}
        }
    }
}
