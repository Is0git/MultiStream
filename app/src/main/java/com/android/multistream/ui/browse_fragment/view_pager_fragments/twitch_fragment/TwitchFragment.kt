package com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment

import android.R.attr
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.R
import com.android.multistream.databinding.TwitchGamesFragmentPageBinding
import com.android.multistream.ui.browse_fragment.BrowseFragment
import com.android.multistream.ui.browse_fragment.BrowseFragmentDirections
import com.android.multistream.utils.ViewModelFactory
import com.android.multistream.utils.pagination.PageLoadingStates
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
        Log.d("TWITCHFRAGMENT", "onCreate")
        twitchFragmentViewModel =
            ViewModelProviders.of(this, factory).get(TwitchFragmentViewModel::class.java)
        binding = TwitchGamesFragmentPageBinding.inflate(inflater, container, false)
        setupList()
        setupObservers()

        return binding.root
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
            topTwitchGamesList.adapter =
                topGamesAdapter.also { it.clickListener = this@TwitchFragment }
//            topTwitchGamesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//                var scrolls = 0
//
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//
//                    super.onScrollStateChanged(recyclerView, newState)
//
//
//                }
//
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//
//
//                    super.onScrolled(recyclerView, dx, dy)
//                    Log.d("SCROLL", "dx: $dy, scrolls: $scrolls")
////                    if (dy > lastDy) (parentFragment as BrowseFragment).deExtendViewPager()
////                    else if (dy < lastDy) (parentFragment as BrowseFragment).extendViewPager()
//
//                    if (dy > 0) {
//                        (parentFragment as BrowseFragment).extendViewPager()
//                        if (twitchFragmentViewModel.getPaginationState() != PageLoadingStates.LOADING) {
//                            val itemsCount =
//                                (binding.topTwitchGamesList.layoutManager as GridLayoutManager).itemCount
//                            val lastVisibleItem =
//                                (binding.topTwitchGamesList.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
//
//                            if (itemsCount - lastVisibleItem - 1 < 10) twitchFragmentViewModel.loadPage()
//                        }
//
//                    }
//
//                    else if (dy < 0 )    (parentFragment as BrowseFragment).deExtendViewPager()
//
//                }
//            })
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
        val directions = BrowseFragmentDirections.actionBrowseFragmentToGameChannelsFragment(
            platformType,
            viewers,
            channels,
            name,
            boxImage,
            gameId
        )
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