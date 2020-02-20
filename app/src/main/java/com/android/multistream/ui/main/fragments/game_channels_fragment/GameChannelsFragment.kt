package com.android.multistream.ui.main.fragments.game_channels_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.GameChannelsFragmentBinding
import com.android.multistream.utils.MIXER
import com.android.multistream.utils.TWITCH
import com.android.multistream.utils.UNKNOWN
import com.android.multistream.utils.ViewModelFactory
import com.android.multistream.utils.pagination.PageLoadingStates
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class GameChannelsFragment : DaggerFragment() {

    val args: GameChannelsFragmentArgs by navArgs()
    var gameId: String? = null
    var platformType: Int = UNKNOWN
    @Inject lateinit var factory: ViewModelFactory
    val twitchChannelsAdapter: TwitchChannelsList by lazy { TwitchChannelsList() }
    lateinit var gameChannelViewModel: GameChannelViewModel
    lateinit var binding: GameChannelsFragmentBinding
    lateinit var navController: NavController
    val mixerChannelsList by lazy { MixerChannelsList() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = args.gameId
        platformType = args.platformType
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gameChannelViewModel = ViewModelProviders.of(this, factory).get(GameChannelViewModel::class.java)
        gameChannelViewModel.apply {
            setGame(gameId)
        }
        binding = GameChannelsFragmentBinding.inflate(inflater, container, false)

        if(platformType == TWITCH) {
            setupTwitchObservers()
            setupTwitchList()
        }
        else if (platformType == MIXER) {
            setupMixerObservers()
            setupMixerList()
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onStart() {
        super.onStart()

    }
    private fun setupTwitchList() {
        binding.topChannelsList.apply {
            adapter = twitchChannelsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (!recyclerView.canScrollVertically(1) && gameChannelViewModel.getLoadState(
                                TWITCH) != PageLoadingStates.LOADING) gameChannelViewModel.loadNextPage(platformType)
                    }
                })
        }
    }

    private fun setupTwitchObservers() {
        gameChannelViewModel.getPagedLoadData()?.observe(viewLifecycleOwner, Observer { twitchChannelsAdapter.list = it })
    }

    private fun setupMixerObservers() {
        gameChannelViewModel.getMixerPagedLoadData().observe(viewLifecycleOwner, Observer { mixerChannelsList.list = it })
    }

    private fun setupMixerList() {
        binding.topChannelsList.apply {
            adapter = mixerChannelsList
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1) && gameChannelViewModel.getLoadState(
                            MIXER) != PageLoadingStates.LOADING) gameChannelViewModel.loadNextPage(platformType)
                }
            })
        }
    }
}
