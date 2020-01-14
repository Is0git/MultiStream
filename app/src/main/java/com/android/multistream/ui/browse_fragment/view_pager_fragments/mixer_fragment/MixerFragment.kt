package com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.R
import com.android.multistream.databinding.MixerGamesFragmentPagerBinding
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.ui.browse_fragment.BrowseFragmentDirections
import com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment.OnGameCategoryListener
import com.android.multistream.util.ViewModelFactory
import com.android.multistream.util.pagination.PageLoadingStates
import com.android.multistream.util.pagination.PagedPositionLoader
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MixerFragment : DaggerFragment(), OnGameCategoryListener {

    lateinit var binding: MixerGamesFragmentPagerBinding
    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var mixerTopGamesAdapter: MixerTopGamesListAdapter
    lateinit var mixerFragmentViewModel: MixerFragmentViewModel

    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mixerFragmentViewModel = ViewModelProviders.of(this, factory).get(MixerFragmentViewModel::class.java)
        binding = MixerGamesFragmentPagerBinding.inflate(inflater, container, false)
        setupList()
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(activity!!, R.id.main_fragment_container)
    }

    private fun setupList() {

        binding.apply {
            topMixerGamesList.adapter = mixerTopGamesAdapter.also { it.onGameCategoryListener = this@MixerFragment }
            topMixerGamesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1) && mixerFragmentViewModel.getState() != PageLoadingStates.LOADING) mixerFragmentViewModel.loadPage()
                }
            })
        }
    }

    private fun setupObservers() {
        mixerFragmentViewModel.pageLiveData.observe(viewLifecycleOwner, Observer { mixerTopGamesAdapter.list = it})
    }

    override fun onGameClick(
        platformType: Int,
        viewers: Int,
        channels: String?,
        name: String,
        boxImage: String?
    ) {
       val directions = BrowseFragmentDirections.actionBrowseFragmentToGameChannelsFragment(platformType, viewers, channels, name, boxImage)
        navController.navigate(directions)
    }
}