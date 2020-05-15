package com.android.multistream.ui.main.fragments.search_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.multistream.di.main_activity.main_fragments.search_fragment.SearchFragmentScope
import com.android.multistream.utils.ResponseHandler
import com.multistream.multistreamsearchview.recent_search.HistoryListAdapter
import com.multistream.multistreamsearchview.search_view.LatestSearchedAdapter
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@SearchFragmentScope
class SearchViewModel @Inject constructor(var repo: SearchRepository) : ViewModel() {

    var searchHistoryLiveData = repo.searchHistoryLiveData

    var latestSearchesLiveData = repo.latestSearchesLiveData

    suspend fun getSearchedTwitchGames(query: String): List<SearchViewLayout.SearchData>? {
        return repo.getSearchedTwitchGames(query)
    }

    suspend fun getSearchedTwitchChannels(
        query: String,
        limit: Int
    ): List<SearchViewLayout.SearchData>? {
        return repo.getSearchedTwitchChannels(query, limit)
    }

    suspend fun getSearchedTwitchStreams(
        query: String,
        limit: Int
    ): List<SearchViewLayout.SearchData>? {
        return repo.getSearchedTwitchStreams(query, limit)
    }

    suspend fun getSearchedMixerChannels(
        query: String,
        limit: Int
    ): List<SearchViewLayout.SearchData>? {
        return repo.getSearchedMixerChannels(query, limit)
    }

    fun addHistoryData(query: String, count: Int) {
        viewModelScope.launch { repo.addHistoryData(query, count) }
    }

    fun deleteHistoryData(historyData: HistoryListAdapter.SearchHistoryData) {
        viewModelScope.launch { repo.deleteHistoryData(historyData) }
    }

    fun addSearchedData(searchData: SearchViewLayout.SearchData) {
        viewModelScope.launch { repo.addSearchedData(searchData) }
    }

    fun clearLatestSearches() {
        viewModelScope.launch { repo.clearLatestSearches() }
    }

    fun deleteItemFromLatestSearches(latestSearchData: LatestSearchedAdapter.LatestSearchData) {
        viewModelScope.launch { repo.deleteItemFromLatestSearches(latestSearchData) }
    }
}