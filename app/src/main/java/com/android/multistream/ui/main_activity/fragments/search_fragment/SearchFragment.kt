package com.android.multistream.ui.main_activity.fragments.search_fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.multistream.R
import com.android.multistream.di.qualifiers.SettingsPreferencesQualifier
import com.android.multistream.network.mixer.models.mixer_channels.MixerGameChannel
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.network.twitch.models.v5.top_games.Game
import com.android.multistream.network.twitch.models.v5.top_games.TopItem
import com.android.multistream.ui.main_activity.MainActivity
import com.android.multistream.ui.main_activity.fragments.home_fragment.HomeFragmentDirections
import com.android.multistream.ui.main_activity.fragments.home_fragment.hide
import com.android.multistream.utils.MIXER
import com.android.multistream.utils.TWITCH
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.example.daggerviewmodelfragment.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.multistream.multistreamsearchview.data_source.DataSource
import com.multistream.multistreamsearchview.databinding.SearchLayoutBinding
import com.multistream.multistreamsearchview.filter.FilterSelection
import com.multistream.multistreamsearchview.search_view.LatestSearchedAdapter
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import com.multistream.multistreamsearchview.search_view.SearchViewLayout.Companion.CHANNELS
import com.multistream.multistreamsearchview.search_view.SearchViewLayout.Companion.GAMES
import com.multistream.multistreamsearchview.search_view.SearchViewLayout.Companion.STREAMS
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class SearchFragment : DaggerViewModelFragment<SearchViewModel>(SearchViewModel::class.java) {

    private lateinit var binding: SearchLayoutBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var searchViewModel: SearchViewModel
    @Inject
    @SettingsPreferencesQualifier
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchLayoutBinding.inflate(inflater, container, false)
        binding.historyList.hide()
        searchViewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        createSourceDownloads()
        createSearchFilters()
        setObservers()
        setEventListeners()
        binding.searchLayout.apply {
            initSearchView()
            registerLifecycle(lifecycle)
        }
        return binding.root
    }

    private fun createSourceDownloads() {
        if (sharedPreferences.getBoolean(getString(R.string.twitch_visibility), false)) {
            val sourceDownloader = DataSource.Builder()
                .setIconDrawable(R.drawable.twitch_small_logo)
                .setName(getString(R.string.twitch))
                .build(SearchViewLayout.SearchData::class.java) {
                    val query = binding.searchLayout.getQuery()
                    val searchedChannels =
                        searchViewModel.getSearchedTwitchChannels(query, 6) ?: emptyList()
                    val searchedGames = searchViewModel.getSearchedTwitchGames(query) ?: emptyList()
                    val searchedStreams =
                        searchViewModel.getSearchedTwitchStreams(query, 6) ?: emptyList()
                    searchedChannels + searchedGames.take(10) + searchedStreams
                }
            binding.searchLayout.addSourceDownloader(sourceDownloader)
        }
        if (sharedPreferences.getBoolean(getString(R.string.mixer_visibility), true)) {
            val sourceDownloader2 = DataSource.Builder()
                .setIconDrawable(R.drawable.mixer_small_logo)
                .setName(getString(R.string.mixer))
                .build(SearchViewLayout.SearchData::class.java) {
                    val query = binding.searchLayout.getQuery()
                    val channels = searchViewModel.getSearchedMixerChannels(query, 5) ?: emptyList()
                    val games = searchViewModel.getSearchedMixerGames(query, 5) ?: emptyList()
                    val streams = searchViewModel.getSearchedMixerStreams(query, 5) ?: emptyList()
                    channels + games + streams
                }
            binding.searchLayout.addSourceDownloader(sourceDownloader2)
        }
    }

    private fun createSearchFilters() {
        val category = FilterSelection.Builder()
            .setFilterSelectionName(getString(R.string.games_category))
            .build(SearchViewLayout.SearchData::class.java) { list -> list.filter { it.category == SearchViewLayout.GAMES } }
        val category2 = FilterSelection.Builder()
            .setFilterSelectionName(getString(R.string.channels_category))
            .build(SearchViewLayout.SearchData::class.java) { list -> list.filter { it.category == SearchViewLayout.CHANNELS } }
        val category3 = FilterSelection.Builder()
            .setFilterSelectionName(getString(R.string.streams_category))
            .build(SearchViewLayout.SearchData::class.java) { list -> list.filter { it.category == SearchViewLayout.STREAMS } }
        val listOfSelectionData1 = listOf(category, category2, category3)
        binding.searchLayout.addFilter(
            getString(R.string.choose_category), listOfSelectionData1,
            isSingleSelection = false,
            isAllSelectionEnabled = true,
            allName = null
        )
        val sortCategory = FilterSelection.Builder()
            .setFilterSelectionName(getString(R.string.asc))
            .build(SearchViewLayout.SearchData::class.java) { list -> list.sortedBy { it.title } }
        val sortCategory2 = FilterSelection.Builder()
            .setFilterSelectionName(getString(R.string.desc))
            .build(SearchViewLayout.SearchData::class.java) { list -> list.sortedByDescending { it.title } }
        binding.searchLayout.addFilter(
            "${getString(R.string.sort)}: ", listOf(sortCategory, sortCategory2),
            isSingleSelection = true,
            isAllSelectionEnabled = true,
            allName = getString(R.string.default_value)
        )
    }

    private fun setObservers() {
        searchViewModel.latestSearchesLiveData.observe(viewLifecycleOwner) {
            binding.noItem.visibility = if (it.isEmpty()) VISIBLE else INVISIBLE
            binding.searchLayout.submitLatestSearchList(
                it
            )
        }
        searchViewModel.searchHistoryLiveData.observe(viewLifecycleOwner) {
            binding.searchLayout.submitHistoryData(it)
        }
    }

    private fun setEventListeners() {
        binding.searchLayout.apply {
            onAddHistoryData = { query, count -> searchViewModel.addHistoryData(query, count) }
            setOnRecentSearchCancelClickListener { position, view ->
                searchViewModel.searchHistoryLiveData.value?.get(position)
                    ?.also { searchViewModel.deleteHistoryData(it) }
            }
            setOnSearchListItemClickListener { position, view ->
                val data = binding.searchLayout.getSearchAdapterItem(position)
                onNavigationItemClick(data)
            }
            setOnClearButtonClickListener { searchViewModel.clearLatestSearches() }
            onSwipe = { position ->
                val item = searchViewModel.latestSearchesLiveData.value?.get(position)
                item?.also { searchViewModel.deleteItemFromLatestSearches(it) }
            }
        }
    }

    private fun onNavigationItemClick(data: SearchViewLayout.SearchData?) {
        lifecycleScope.launch {
            val navController = findNavController()
            data?.let {
                viewModel.addSearchedData(data)
                when (it.platform) {
                    TWITCH -> {
                        when (it.category) {
                            GAMES -> {
                                val directions =
                                    HomeFragmentDirections.actionStartToTwitchGameCategory(
                                        TopItem.createTopItem(it.id, it.title, it.imageUrl, 0, 0)
                                    )
                                navController.navigate(directions)
                            }
                            CHANNELS -> {
                                val directions =
                                    SearchFragmentDirections.actionSearchFragmentToTwitchProfileFragment(
                                        StreamsItem.buildStreamItem(it.id, it.title, it.imageUrl)
                                    )
                                navController.navigate(directions)
                            }
                            STREAMS -> {
                                (requireActivity() as MainActivity).initLiveStreamPlayerFragment(
                                    null,
                                    it.title?.toLowerCase(Locale.ROOT),
                                    it.imageUrl,
                                    null,
                                    it.title,
                                    it.id.toString(),
                                    null
                                )
                            }
                        }
                    }
                    MIXER -> {
                        when (it.category) {
                            GAMES -> {
                                val directions =
                                    HomeFragmentDirections.actionStartToMixerGameCategory(
                                        MixerTopGames.buildMixerTopGames(
                                            it.id,
                                            it.title,
                                            it.imageUrl,
                                            null,
                                            null
                                        )
                                    )
                                navController.navigate(directions)
                            }
                            CHANNELS -> {
                                val directions =
                                    SearchFragmentDirections.actionSearchFragmentToMixerProfileFragment(
                                        MixerGameChannel.buildMixerGame(
                                            it.id,
                                            it.title,
                                            it.imageUrl
                                        )
                                    )
                                navController.navigate(directions)
                            }
                            STREAMS -> {
                                Snackbar.make(
                                    binding.searchList,
                                    "mixer player is disabled",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.searchLayout.searchView.apply {
            onActionViewCollapsed()
        }
        super.onDestroyView()
    }
}