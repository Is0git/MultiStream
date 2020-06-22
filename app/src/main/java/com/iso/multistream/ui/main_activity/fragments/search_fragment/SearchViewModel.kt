package com.iso.multistream.ui.main_activity.fragments.search_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iso.multistream.di.main_activity.main_fragments.search_fragment.SearchFragmentScope
import com.multistream.multistreamsearchview.recent_search.HistoryListAdapter
import com.multistream.multistreamsearchview.search_result.SearchListAdapter
import com.multistream.multistreamsearchview.search_view.LatestSearchedAdapter
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import kotlinx.coroutines.launch
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

    suspend fun getSearchedMixerGames(
        query: String,
        limit: Int
    ): List<SearchViewLayout.SearchData>? {
        return repo.getSearchedMixerGames(query, limit)
    }

    suspend fun getSearchedMixerStreams(
        query: String,
        limit: Int
    ): List<SearchListAdapter.StreamSearchData>? {
        return repo.getSearchedMixerStreams(query, limit)
    }

    fun addHistoryData(query: String, count: Int) {
        viewModelScope.launch { repo.addHistoryData(query, count) }
    }

    fun deleteHistoryData(historyData: HistoryListAdapter.SearchHistoryData) {
        viewModelScope.launch { repo.deleteHistoryData(historyData) }
    }

    suspend fun addSearchedData(searchData: SearchViewLayout.SearchData) {
        repo.addSearchedData(searchData)
    }

    fun clearLatestSearches() {
        viewModelScope.launch { repo.clearLatestSearches() }
    }

    fun deleteItemFromLatestSearches(latestSearchData: LatestSearchedAdapter.LatestSearchData) {
        viewModelScope.launch { repo.deleteItemFromLatestSearches(latestSearchData) }
    }
}