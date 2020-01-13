package com.android.multistream.ui.browse_fragment.view_pager_fragments.top_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.GamesTopFragmentPageBinding
import com.android.multistream.network.twitch.models.Data
import com.android.multistream.network.twitch.models.v5.TopItem
import com.android.multistream.util.ViewModelFactory
import com.android.multistream.util.pagination.PagedOffsetLoader
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TopGamesFragment : DaggerFragment() {
    lateinit var binding: GamesTopFragmentPageBinding
    @Inject lateinit var factory: ViewModelFactory
    lateinit var topFragmentViewModel: TopFragmentViewModel
    @Inject lateinit var topGamesAdapter: TopGamesListAdapter

    lateinit var offSetPager: PagedOffsetLoader<Data>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        topFragmentViewModel = ViewModelProviders.of(this, factory).get(TopFragmentViewModel::class.java)
        binding = GamesTopFragmentPageBinding.inflate(inflater, container, false)
        setupList()
        setupObservers()
        return binding.root
    }

    fun setupList() {
        offSetPager = PagedOffsetLoader(topFragmentViewModel.listener, 20)
        binding.apply {
            topGamesList.adapter = topGamesAdapter
            topGamesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) offSetPager.loadHandler()
                }
            })
        }
    }

    fun setupObservers() {
        offSetPager.dataLiveData.observe(viewLifecycleOwner, Observer { topGamesAdapter.list = it })
    }
}