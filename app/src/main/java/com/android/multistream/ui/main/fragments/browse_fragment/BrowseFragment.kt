package com.android.multistream.ui.main.fragments.browse_fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.multistream.R
import com.android.multistream.databinding.BrowseFragmentBinding
import com.android.multistream.utils.ScreenUnit
import com.android.multistream.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class BrowseFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var browseViewModel: BrowseFragmentViewModel
    lateinit var binding: BrowseFragmentBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        browseViewModel = ViewModelProvider(this, factory).get(BrowseFragmentViewModel::class.java)
        binding = BrowseFragmentBinding.inflate(inflater, container, false)
        setupViewPager()
        handleTabLayout()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }


    private fun setupViewPager() {
        binding.topGamesViewPager.adapter =
            GamesFragmentsViewPagerAdapter(childFragmentManager, lifecycle)
    }

    private fun handleTabLayout() {
        binding.stripeTabLayout.setupWithViewPager(binding.topGamesViewPager) { tab, i ->
            val id = when (i) {
                0 -> R.drawable.youtube
                1 -> R.drawable.twitch
                2 -> R.drawable.mixer
                else -> return@setupWithViewPager
            }
            tab.setCustomView(R.layout.default_tab)
            tab.customView?.findViewById<ImageView>(R.id.icon)
                ?.setImageDrawable(getDrawable(activity?.baseContext!!, id))
        }
    }


}