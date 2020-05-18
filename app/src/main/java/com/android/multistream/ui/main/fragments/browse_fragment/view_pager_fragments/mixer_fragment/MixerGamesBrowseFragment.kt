package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.mixer_fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.multistream.R
import com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.GamesBrowseFragment
import com.example.pagination.PageLoader
import com.example.pagination.PageLoadingStates
import com.example.pagination.attach
import com.multistream.multistreamsearchview.search_view.OnItemClickListener
import javax.inject.Inject

class MixerGamesBrowseFragment : GamesBrowseFragment<MixerGamesBrowseViewModel>(MixerGamesBrowseViewModel::class.java), OnItemClickListener {

    @Inject
    lateinit var mixerTopGamesAdapter: MixerTopGamesListAdapter
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observe()
        navController =
            Navigation.findNavController(requireActivity(), R.id.main_fragment_container)
    }

    private fun setupList() {
        binding.apply {
            topGamesList attach viewModel.pageLoader
            topGamesList.adapter = mixerTopGamesAdapter
        }
    }

    override fun getPageLoader(): PageLoader<*> {
        return viewModel.pageLoader
    }

    override fun observe() {
        viewModel.pageLoader.dataLiveData.observe(viewLifecycleOwner) {
            binding.searchNoItem.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            mixerTopGamesAdapter.list = it
        }
    }

    override fun getPageLoadStateLiveData(): LiveData<PageLoadingStates> {
        return viewModel.pageLoader.pageLoadingState
    }

    override fun onClick(position: Int, view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}