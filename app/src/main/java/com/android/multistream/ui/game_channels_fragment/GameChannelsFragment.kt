package com.android.multistream.ui.game_channels_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.GameChannelsFragmentBinding
import com.android.multistream.util.ViewModelFactory
import com.android.multistream.util.pagination.PageLoadingStates
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class GameChannelsFragment : DaggerFragment() {

    val args: GameChannelsFragmentArgs by navArgs()
    @Inject lateinit var factory: ViewModelFactory
    @Inject lateinit var twitchChannelsAdapter: TwitchChannelsList
    lateinit var gameChannelViewModel: GameChannelViewModel
    lateinit var binding: GameChannelsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gameChannelViewModel = ViewModelProviders.of(this, factory).get(GameChannelViewModel::class.java)
        binding = GameChannelsFragmentBinding.inflate(inflater, container, false)
        setupObservers()
        setupList()
        return binding.root
    }

    private fun setupList() {
        binding.topChannelsList.apply {
            adapter = twitchChannelsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (!recyclerView.canScrollVertically(1) && gameChannelViewModel.getLoadState() != PageLoadingStates.LOADING) gameChannelViewModel.loadNextPage()
                    }
                })
        }
    }

    private fun setupObservers() {
        gameChannelViewModel.pageLoadLiveData?.observe(viewLifecycleOwner, Observer { twitchChannelsAdapter.list = it })
    }
}