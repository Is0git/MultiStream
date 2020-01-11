package com.android.multistream.ui.game_channels_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.multistream.databinding.GameChannelsFragmentBinding
import dagger.android.support.DaggerFragment

class GameChannelsFragment : DaggerFragment() {


    lateinit var binding: GameChannelsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameChannelsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}