package com.android.multistream.ui.browse_fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragment
import com.android.multistream.ui.browse_fragment.view_pager_fragments.top_fragment.TopGamesFragment
import com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragment

class GamesFragmentsViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment {
     val fragment = when(position) {
          0 -> TopGamesFragment()
          1 -> TwitchFragment()
          2 -> MixerFragment()
         else -> null
      }
        return  fragment!!
    }
}