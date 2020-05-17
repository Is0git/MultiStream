package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.twitch_fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.multistream.R
import com.android.multistream.ui.main.fragments.browse_fragment.GamesBrowseFragment
import com.example.pagination.PageLoader
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
        navController =
            Navigation.findNavController(requireActivity(), R.id.main_fragment_container)
    }

    private fun setupList() {
        binding.apply {
            topGamesList attach viewModel.repo.pageLoader
            topGamesList.adapter = topGamesAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.topGamesList detach viewModel.pageLoader
    }

    override fun getPageLoader(): PageLoader<*> {
        return viewModel.pageLoader
    }

    override fun observe() {
        viewModel.pageLoader.dataLiveData.observe(viewLifecycleOwner) {
            binding.searchNoItem.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            topGamesAdapter.list = it
        }
    }
}