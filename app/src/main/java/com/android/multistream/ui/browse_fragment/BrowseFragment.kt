package com.android.multistream.ui.browse_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.multistream.databinding.BrowseFragmentBinding
import com.android.multistream.util.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class BrowseFragment : DaggerFragment() {

    @Inject lateinit var factory: ViewModelFactory
    lateinit var browseViewModel: BrowseFragmentViewModel
    lateinit var binding: BrowseFragmentBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        browseViewModel = ViewModelProviders.of(this, factory).get(BrowseFragmentViewModel::class.java)
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
        binding.topGamesViewPager.adapter = GamesFragmentsViewPagerAdapter(childFragmentManager, lifecycle)
    }

    private fun handleTabLayout() {
        binding.apply {
            TabLayoutMediator(topGamesTabLayout, topGamesViewPager) {tab, position ->
               tab.text = when(position) {
                   0 -> "Top"
                   1 -> "Twitch"
                   2 -> "Mixer"
                   else -> "None"
               }
            }.attach()
        }
    }
}