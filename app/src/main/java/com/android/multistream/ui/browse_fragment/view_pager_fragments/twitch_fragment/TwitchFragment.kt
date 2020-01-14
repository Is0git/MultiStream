package com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment

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
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.R
import com.android.multistream.databinding.TwitchGamesFragmentPageBinding
import com.android.multistream.network.twitch.models.v5.TopItem
import com.android.multistream.ui.browse_fragment.BrowseFragmentDirections
import com.android.multistream.util.ViewModelFactory
import com.android.multistream.util.pagination.PageLoadingStates
import com.android.multistream.util.pagination.PagedOffsetLoader
import com.android.multistream.util.pagination.PagedPositionLoader
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class TwitchFragment : DaggerFragment(), TwitchOnGameCategoryListener {

    lateinit var binding: TwitchGamesFragmentPageBinding
    @Inject lateinit var factory: ViewModelFactory
    lateinit var twitchFragmentViewModel: TwitchFragmentViewModel
    @Inject lateinit var topGamesAdapter: TwitchTopGamesAdapter
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TWITCHFRAGMENT", "onCreate")
        twitchFragmentViewModel = ViewModelProviders.of(this, factory).get(TwitchFragmentViewModel::class.java)
        binding = TwitchGamesFragmentPageBinding.inflate(inflater, container, false)
        setupList()
        setupObservers()
      return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TWITCHFRAGMENT", "onCreateView")
        navController = Navigation.findNavController(activity!!, R.id.main_fragment_container)
    }

    override fun onStart() {
        super.onStart()
        Log.d("TWITCHFRAGMENT", "onStart")
    }

    private fun setupList() {
        binding.apply {
            topTwitchGamesList.adapter = topGamesAdapter.also { it.clickListener = this@TwitchFragment }
            topTwitchGamesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1) && twitchFragmentViewModel.getPaginationState() != PageLoadingStates.LOADING) twitchFragmentViewModel.loadPage()
                }
            })
        }
    }

    private fun setupObservers() {
        twitchFragmentViewModel.paginationLiveData.observe(viewLifecycleOwner, Observer { topGamesAdapter.list = it})
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TWITCHFRAGMENT", "onDestroy")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("TWITCHFRAGMENT", "onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("TWITCHFRAGMENT", "onDeatch")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TWITCHFRAGMENT", "onDestroyView")
    }
}