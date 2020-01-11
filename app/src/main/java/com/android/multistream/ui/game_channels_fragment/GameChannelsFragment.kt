package com.android.multistream.ui.game_channels_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.android.multistream.databinding.GameChannelsFragmentBinding
import dagger.android.support.DaggerFragment

class GameChannelsFragment : DaggerFragment() {

    val args: GameChannelsFragmentArgs by navArgs()
    lateinit var binding: GameChannelsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameChannelsFragmentBinding.inflate(inflater, container, false)
        binding.testText.text = args.data?.name
        return binding.root
    }
}