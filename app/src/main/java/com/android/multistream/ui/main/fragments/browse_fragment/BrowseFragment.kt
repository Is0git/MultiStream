package com.android.multistream.ui.main.fragments.browse_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.multistream.R
import com.android.multistream.databinding.BrowseFragmentBinding
import dagger.android.support.DaggerFragment


class BrowseFragment : DaggerFragment() {

    lateinit var binding: BrowseFragmentBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                0 -> R.drawable.twitch
                1 -> R.drawable.mixer
                else -> return@setupWithViewPager
            }
            tab.setCustomView(R.layout.default_tab)
            tab.customView?.findViewById<ImageView>(R.id.icon)
                ?.setImageDrawable(getDrawable(activity?.baseContext!!, id))
        }
    }
}