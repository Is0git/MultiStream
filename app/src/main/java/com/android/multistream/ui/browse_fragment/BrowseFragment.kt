package com.android.multistream.ui.browse_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.BrowseFragmentBinding
import com.android.multistream.network.twitch.models.Data
import com.android.multistream.util.ViewModelFactory
import com.android.multistream.util.pagination.PagedOffsetLoader
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class BrowseFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var topGamesAdapter: TopGamesListAdapter
    lateinit var browseViewModel: BrowseFragmentViewModel
    lateinit var topGamesPagination: PagedOffsetLoader<Data>
    lateinit var binding: BrowseFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        browseViewModel = ViewModelProviders.of(this, factory).get(BrowseFragmentViewModel::class.java)
        binding = BrowseFragmentBinding.inflate(inflater, container, false)
        setupTopGamesList()
        return binding.root
    }

    private fun setupTopGamesList() {
        topGamesPagination = PagedOffsetLoader<Data>(browseViewModel.paginationListener)

        binding.apply {
            topGamesList.adapter = topGamesAdapter
            topGamesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) topGamesPagination.loadHandler()
                }
            })
        }
        topGamesPagination.dataLiveData.observe(
            viewLifecycleOwner,
            Observer { topGamesAdapter.list = it })
    }
}