package com.android.multistream.ui.game_channels_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.android.multistream.databinding.GameChannelsFragmentBinding
import com.android.multistream.util.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class GameChannelsFragment : DaggerFragment() {

    val args: GameChannelsFragmentArgs by navArgs()
    @Inject lateinit var factory: ViewModelFactory
    lateinit var gameChannelViewModel: GameChannelViewModel
    lateinit var binding: GameChannelsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gameChannelViewModel = ViewModelProviders.of(this, factory).get(GameChannelViewModel::class.java)
        binding = GameChannelsFragmentBinding.inflate(inflater, container, false)
        binding.testText.text = args.data?.name
        return binding.root
    }
}