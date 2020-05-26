package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.twitch_fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.android.multistream.R
import com.android.multistream.ui.main.fragments.browse_fragment.BrowseFragmentDirections
import com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.GamesBrowseFragment
import com.example.pagination.PageLoader
import com.example.pagination.PageLoadingStates
import com.example.pagination.attach
import com.example.pagination.detach
import javax.inject.Inject


class TwitchGamesBrowseFragment : GamesBrowseFragment<TwitchGamesBrowseViewModel>(
    TwitchGamesBrowseViewModel::class.java
) {

    @Inject
    lateinit var topGamesAdapter: TwitchTopGamesAdapter
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        viewModel.pageLoader.loadInit()
        navController = findNavController()
    }

    override fun onClick(position: Int, view: View) {
        val item = topGamesAdapter.list?.get(position)
        val directions = BrowseFragmentDirections.actionBrowseFragmentToTwitchGameCategory(item)
        navController.navigate(directions)
    }

    private fun setupList() {
        binding.apply {
            topGamesList attach viewModel.repo.pageLoader
            topGamesList.adapter = topGamesAdapter.also { it.clickListener = this@TwitchGamesBrowseFragment }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.topGamesList detach viewModel.pageLoader
    }

    override fun getPageLoader(): PageLoader<*> {
        return viewModel.pageLoader
    }

    override fun observeData() {
        viewModel.pageLoader.dataLiveData.observe(viewLifecycleOwner) {
            binding.searchNoItem.visibility = if (it != null && it.isEmpty()) View.VISIBLE else View.INVISIBLE
            topGamesAdapter.list = it
        }
    }

    override fun getPageLoadStateLiveData(): LiveData<PageLoadingStates> {
        return viewModel.pageLoader.pageLoadingState
    }
}