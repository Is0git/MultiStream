package com.android.multistream.ui.main.fragments.search_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.android.multistream.R
import com.android.multistream.utils.ViewModelFactory
import com.multistream.multistreamsearchview.data_source.DataSource
import com.multistream.multistreamsearchview.databinding.SearchLayoutBinding
import com.multistream.multistreamsearchview.filter.FilterSelection
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import dagger.android.support.DaggerFragment
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class SearchFragment : DaggerFragment() {
    private lateinit var binding: SearchLayoutBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchLayoutBinding.inflate(inflater, container, false)
        searchViewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        val sourceDownloader = DataSource.Builder()
            .setIconDrawable(R.drawable.twitch_small_logo)
            .setName("Twitch")
            .build(SearchViewLayout.SearchData::class.java) {
                val query = binding.searchLayout.getQuery()
                val searchedChannels = searchViewModel.getSearchedTwitchChannels(
                    query,
                    6
                ) ?: emptyList()
                val searchedGames = searchViewModel.getSearchedTwitchGames(query) ?: emptyList()
                val searchedStreams =
                    searchViewModel.getSearchedTwitchStreams(query, 6) ?: emptyList()
                searchedChannels + searchedGames.take(10) + searchedStreams
            }
        val sourceDownloader2 = DataSource.Builder()
            .setIconDrawable(R.drawable.mixer_small_logo)
            .setName("Mixer")
            .build(SearchViewLayout.SearchData::class.java) {
                val query = binding.searchLayout.getQuery()
                searchViewModel.getSearchedMixerChannels(query, 5)
            }
        binding.searchLayout.addSourceDownloader(sourceDownloader)
        binding.searchLayout.latestSearchesAdapter
        binding.searchLayout.addSourceDownloader(sourceDownloader2)
        val category = FilterSelection.Builder()
            .setFilterSelectionName("Games")
            .build(SearchViewLayout.SearchData::class.java) { list -> list.filter { it.category == SearchViewLayout.GAMES } }
        val category2 = FilterSelection.Builder()
            .setFilterSelectionName("Channels")
            .build(SearchViewLayout.SearchData::class.java) { list -> list.filter { it.category == SearchViewLayout.CHANNELS } }
        val category3 = FilterSelection.Builder()
            .setFilterSelectionName("Streams")
            .build(SearchViewLayout.SearchData::class.java) { list -> list.filter { it.category == SearchViewLayout.STREAMS } }
        val listOfSelectionData1 = listOf(category, category2, category3)
        binding.searchLayout.addFilter(
            "Choose category", listOfSelectionData1,
            isSingleSelection = false,
            isAllSelectionEnabled = true
        )
        val sortCategory = FilterSelection.Builder()
            .setFilterSelectionName("Asc")
            .build(SearchViewLayout.SearchData::class.java) { list -> list.sortedBy { it.title } }
        val sortCategory2 = FilterSelection.Builder()
            .setFilterSelectionName("DESC")
            .build(SearchViewLayout.SearchData::class.java) { list -> list.sortedByDescending { it.title } }
        binding.searchLayout.addFilter(
            "Sort: ", listOf(sortCategory, sortCategory2),
            isSingleSelection = true,
            isAllSelectionEnabled = true
        )
        binding.searchLayout.apply {
            onAddHistoryData = { query, count -> searchViewModel.addHistoryData(query, count) }
            setOnRecentSearchCancelClickListener { position, view ->
                searchViewModel.searchHistoryLiveData.value?.get(position)
                    ?.also { searchViewModel.deleteHistoryData(it) }
            }
            setOnSearchListItemClickListener { position, view ->
                val data = binding.searchLayout.getSearchAdapterItem(position)
                data?.also { searchViewModel.addSearchedData(it) }
            }
            searchViewModel.latestSearchesLiveData.observe(viewLifecycleOwner) {
                submitLatestSearchList(
                    it
                )
            }
            setOnClearButtonClickListener { searchViewModel.clearLatestSearches() }
            onSwipe = { position ->
                val item = searchViewModel.latestSearchesLiveData.value?.get(position)
                item?.also { searchViewModel.deleteItemFromLatestSearches(it) }
            }
            initSearchView()
        }
        searchViewModel.searchHistoryLiveData.observe(viewLifecycleOwner) {
            binding.searchLayout.submitHistoryData(it)
        }
        return binding.root
    }
}