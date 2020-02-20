package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.top_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.R
import com.android.multistream.databinding.GamesTopFragmentPageBinding
import com.android.multistream.network.twitch.models.Data
import com.android.multistream.ui.main.fragments.browse_fragment.BrowseFragmentDirections
import com.android.multistream.utils.ViewModelFactory
import com.android.multistream.utils.pagination.PageLoadingStates
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TopGamesFragment : DaggerFragment(), CategoryNavigationListener<Data> {
    lateinit var binding: GamesTopFragmentPageBinding
    @Inject lateinit var factory: ViewModelFactory
    lateinit var topFragmentViewModel: TopFragmentViewModel
    @Inject lateinit var topGamesAdapter: TopGamesListAdapter
    lateinit var navController: NavController

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(
            activity!!,
            R.id.main_fragment_container
        )
    }
    fun setupList() {
        binding.apply {
            topGamesList.adapter = topGamesAdapter.also { it.listener = this@TopGamesFragment }
            topGamesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1) && topFragmentViewModel.getPaginationSate() != PageLoadingStates.LOADING) topFragmentViewModel.loadPage()
                }
            })
        }
    }

    fun setupObservers() {
        topFragmentViewModel.pageLiveData.observe(viewLifecycleOwner, Observer { topGamesAdapter.list = it })
    }

    override fun onGameClick(data: Data) {
       val directions = BrowseFragmentDirections.actionBrowseFragmentToGameChannelsFragment2(data, 0, 0, null, null, null)
        navController.navigate(directions)
    }
}