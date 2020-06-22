package com.iso.multistream.ui.main_activity.fragments.browse_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.overscrollbehavior.OverScrollBehavior
import com.iso.multistream.R
import com.iso.multistream.databinding.BrowseFragmentBinding
import com.iso.multistream.ui.main_activity.fragments.browse_fragment.view_pager_fragments.GamesBrowseFragment
import dagger.android.support.DaggerFragment


class BrowseFragment : DaggerFragment(), OverScrollBehavior.OverScrollListener {

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
        ((binding.topGamesViewPager.layoutParams as CoordinatorLayout.LayoutParams).behavior as OverScrollBehavior).overScrollListener =
            this
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

    private fun invalidateViewPagerItem() {
        val position = binding.topGamesViewPager.currentItem
        val pageLoader =
            (childFragmentManager.fragments[position] as GamesBrowseFragment<*>).getPageLoader()
        pageLoader.invalidate(true)
    }

    override fun onOverScrollCompleted() {
        invalidateViewPagerItem()
    }

    override fun onOverScrollStart() {

    }
}