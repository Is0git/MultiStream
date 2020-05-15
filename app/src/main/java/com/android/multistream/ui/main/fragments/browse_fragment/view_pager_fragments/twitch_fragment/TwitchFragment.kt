package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.twitch_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.R
import com.android.multistream.databinding.TwitchGamesFragmentPageBinding
import com.android.multistream.ui.main.fragments.browse_fragment.BrowseFragmentDirections
import com.android.multistream.utils.ViewModelFactory
import com.android.multistream.pagination.PageLoadingStates
import com.android.multistream.pagination.attach
import com.android.multistream.pagination.detach
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class TwitchFragment : DaggerFragment(), OnGameCategoryListener {

    lateinit var binding: TwitchGamesFragmentPageBinding
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var twitchFragmentViewModel: TwitchFragmentViewModel
    @Inject
    lateinit var topGamesAdapter: TwitchTopGamesAdapter
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        twitchFragmentViewModel = ViewModelProvider(this, factory).get(TwitchFragmentViewModel::class.java)
        binding = TwitchGamesFragmentPageBinding.inflate(inflater, container, false)
        setupList()
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.main_fragment_container)
    }


    private fun setupList() {
        binding.apply {
            topTwitchGamesList attach twitchFragmentViewModel.repo.pageLoader
            topTwitchGamesList.adapter = topGamesAdapter.also { it.clickListener = this@TwitchFragment }

        }

    }

    private fun setupObservers() {
        twitchFragmentViewModel.paginationLiveData.observe(
            viewLifecycleOwner,
            Observer { topGamesAdapter.list = it })
    }

    override fun onGameClick(
        platformType: Int,
        viewers: Int,
        channels: String?,
        name: String,
        boxImage: String?,
        gameId: String?
    ) {

    }


    override fun onDestroy() {
        super.onDestroy()
        binding.topTwitchGamesList detach twitchFragmentViewModel.pageLoader
    }
}