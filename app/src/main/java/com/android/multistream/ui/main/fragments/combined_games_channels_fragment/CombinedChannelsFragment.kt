package com.android.multistream.ui.main.fragments.combined_games_channels_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.multistream.databinding.CombinedGamesTopChannelsBinding
import dagger.android.support.DaggerFragment

class CombinedChannelsFragment : DaggerFragment() {
        lateinit var binding: CombinedGamesTopChannelsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CombinedGamesTopChannelsBinding.inflate(inflater, container, false)
        return binding.root
}
}