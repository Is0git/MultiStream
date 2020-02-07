package com.android.multistream.ui.browse_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.multistream.R
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
        Log.d("TWITCHFRAGMENT", "onCreateView2")
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
        binding.stripeTabLayout.setupWithViewPager(binding.topGamesViewPager) { tab, i ->
            val id = when (i) {
                0 -> R.drawable.youtube
                1 -> R.drawable.twitch
                2 -> R.drawable.mixer
                else -> return@setupWithViewPager
            }

            tab.setCustomView(R.layout.default_tab)
            tab.customView?.findViewById<ImageView>(R.id.icon)?.setImageDrawable(getDrawable(activity?.baseContext!!, id))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TWITCHFRAGMENT", "onDestroy2")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("TWITCHFRAGMENT", "onAttach2")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("TWITCHFRAGMENT", "onDeatch2")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TWITCHFRAGMENT", "onDestroyView2")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TWITCHFRAGMENT", "onCreate2")
        super.onCreate(savedInstanceState)
    }
}