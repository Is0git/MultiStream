package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.mixer_fragment

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
import com.android.multistream.databinding.MixerGamesFragmentPagerBinding
import com.android.multistream.ui.main.fragments.browse_fragment.BrowseFragmentDirections
import com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.twitch_fragment.OnGameCategoryListener
import com.android.multistream.utils.ViewModelFactory
import com.android.multistream.pagination.PageLoadingStates
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
        mixerFragmentViewModel =
            ViewModelProviders.of(this, factory).get(MixerFragmentViewModel::class.java)
        binding = MixerGamesFragmentPagerBinding.inflate(inflater, container, false)
        setupList()
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity()!!, R.id.main_fragment_container)
    }

    private fun setupList() {
        binding.apply {
            topMixerGamesList.adapter =
                mixerTopGamesAdapter.also { it.onGameCategoryListener = this@MixerFragment }
        }
    }

    private fun setupObservers() {
        mixerFragmentViewModel.pageLiveData.observe(
            viewLifecycleOwner,
            Observer { mixerTopGamesAdapter.list = it })
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
}