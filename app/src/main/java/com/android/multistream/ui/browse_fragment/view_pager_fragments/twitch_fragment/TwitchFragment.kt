package com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.TwitchGamesFragmentPageBinding
import com.android.multistream.network.twitch.models.v5.TopItem
import com.android.multistream.util.ViewModelFactory
import com.android.multistream.util.pagination.PagedOffsetLoader
import com.android.multistream.util.pagination.PagedPositionLoader
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TwitchFragment : DaggerFragment() {

    lateinit var binding: TwitchGamesFragmentPageBinding
    @Inject lateinit var factory: ViewModelFactory
    lateinit var twitchFragmentViewModel: TwitchFragmentViewModel
    lateinit var pagedOffSetLoader: PagedOffsetLoader<TopItem>
    @Inject lateinit var topGamesAdapter: TwitchTopGamesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        twitchFragmentViewModel = ViewModelProviders.of(this, factory).get(TwitchFragmentViewModel::class.java)
        binding = TwitchGamesFragmentPageBinding.inflate(inflater, container, false)
        setupList()
        setupObservers()
      return  binding.root
    }

    private fun setupList() {
        pagedOffSetLoader = PagedOffsetLoader(twitchFragmentViewModel.listener, 20)
        binding.apply {
            topTwitchGamesList.adapter = topGamesAdapter
            topTwitchGamesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1) && !recyclerView.hasPendingAdapterUpdates()) pagedOffSetLoader.loadHandler()
                }
            })
        }
    }

    private fun setupObservers() {
        pagedOffSetLoader.dataLiveData.observe(viewLifecycleOwner, Observer { topGamesAdapter.list = it})
    }
}