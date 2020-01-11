package com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.multistream.databinding.TwitchGamesFragmentPageBinding
import dagger.android.support.DaggerFragment

class TwitchFragment : DaggerFragment() {

    lateinit var binding: TwitchGamesFragmentPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TwitchGamesFragmentPageBinding.inflate(inflater, container, false)
      return  binding.root
    }
}