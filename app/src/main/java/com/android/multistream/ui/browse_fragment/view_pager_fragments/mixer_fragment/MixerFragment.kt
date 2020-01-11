package com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.MixerGamesFragmentPagerBinding
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.util.ViewModelFactory
import com.android.multistream.util.pagination.PagedOffsetLoader
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MixerFragment : DaggerFragment() {

    lateinit var binding: MixerGamesFragmentPagerBinding
    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var mixerTopGamesAdapter: MixerTopGamesListAdapter
    lateinit var mixerFragmentViewModel: MixerFragmentViewModel
    lateinit var mixerTopGamesPagination: PagedOffsetLoader<MixerTopGames>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mixerFragmentViewModel =
            ViewModelProviders.of(this, factory).get(MixerFragmentViewModel::class.java)
        mixerTopGamesPagination = PagedOffsetLoader(mixerFragmentViewModel.pagedOffSetListener)
        binding = MixerGamesFragmentPagerBinding.inflate(inflater, container, false)
        setupList()
        setupObservers()
        return binding.root
    }

    private fun setupList() {
        binding.apply {
            topMixerGamesList.adapter = mixerTopGamesAdapter
            topMixerGamesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) mixerTopGamesPagination.loadHandler()
                }
            })
        }
    }

    private fun setupObservers() {
        mixerTopGamesPagination.dataLiveData.observe(viewLifecycleOwner, Observer { mixerTopGamesAdapter.list = it})
    }
}