package com.android.multistream.ui.main.fragments.browse_fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.mixer_fragment.MixerGamesBrowseFragment
import com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.twitch_fragment.TwitchGamesBrowseFragment

class GamesFragmentsViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TwitchGamesBrowseFragment()
            1 -> MixerGamesBrowseFragment()
            else -> TwitchGamesBrowseFragment()
        }
    }

}