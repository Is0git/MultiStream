package com.android.multistream.ui.main.fragments.game_channels_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.multistream.databinding.GameChannelsFragmentBinding
import com.android.multistream.utils.UNKNOWN
import com.example.daggerviewmodelfragment.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class GameChannelsFragment : DaggerFragment() {

    var gameId: String? = null
    var platformType: Int = UNKNOWN
    @Inject
    lateinit var factory: ViewModelFactory
    private val twitchChannelsAdapter: TwitchChannelsList by lazy { TwitchChannelsList() }
    lateinit var gameChannelViewModel: GameChannelViewModel
    lateinit var binding: GameChannelsFragmentBinding
    lateinit var navController: NavController
    private val mixerChannelsList by lazy { MixerChannelsList() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gameChannelViewModel =
            ViewModelProviders.of(this, factory).get(GameChannelViewModel::class.java)
        binding = GameChannelsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

}
