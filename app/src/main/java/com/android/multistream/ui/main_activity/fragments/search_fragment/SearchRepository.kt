package com.android.multistream.ui.main_activity.fragments.search_fragment

import android.app.Application
import com.android.multistream.R
import com.android.multistream.database.dao.SearchDao
import com.android.multistream.di.main_activity.main_fragments.search_fragment.SearchFragmentScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.utils.ResponseHandler.execute
import com.multistream.multistreamsearchview.recent_search.HistoryListAdapter
import com.multistream.multistreamsearchview.search_result.SearchListAdapter
import com.multistream.multistreamsearchview.search_view.LatestSearchedAdapter
import com.multistream.multistreamsearchview.search_view.SearchViewLayout
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@SearchFragmentScope
class SearchRepository @Inject constructor(
    val mixerService: MixerService, var twitchService: TwitchService,
    val application: Application,
    private val searchDao: SearchDao
) {

    private val searchDateFormat: SimpleDateFormat by lazy {
        SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.ENGLISH
        )
    }

    var searchHistoryLiveData = searchDao.getSearchesHistory()
    var latestSearchesLiveData = searchDao.getLatestSearches()

    suspend fun getSearchedTwitchGames(query: String): List<SearchViewLayout.SearchData>? {
        return execute(application) { twitchService.getGamesSearches(query) }
    }

    suspend fun getSearchedTwitchChannels(
        query: String,
        limit: Int
    ): List<SearchViewLayout.SearchData>? {
        return execute(application) { twitchService.getChannelSearches(query, limit, 0) }
    }

    suspend fun getSearchedTwitchStreams(
        query: String,
        limit: Int
    ): List<SearchViewLayout.SearchData>? {
        return execute(application) {
            twitchService.getStreamSearches(query, limit, 0) }
    }

    suspend fun getSearchedMixerChannels(
        query: String,
        limit: Int
    ): List<SearchViewLayout.SearchData>? {
        return execute(application) { mixerService.getSearchedChannels(query, limit) }
    }

    suspend fun getSearchedMixerGames(
        query: String,
        limit: Int
    ): List<SearchViewLayout.SearchData>? {
        return execute(application) { mixerService.getSearchedGames(query, limit) }
    }

    suspend fun getSearchedMixerStreams(
        query: String,
        limit: Int
    ): List<SearchListAdapter.StreamSearchData>? {
        return execute(application) { mixerService.getSearchedStreams(query, limit) }
    }

    suspend fun addHistoryData(query: String, count: Int) {
        val historyData = HistoryListAdapter.SearchHistoryData(
            0,
            query,
            System.currentTimeMillis(),
            count,
            null,
            convertTimeToString(System.currentTimeMillis())
        )
        searchDao.addHistoryData(historyData)
    }

    private fun convertTimeToString(time: Long): String {
        return searchDateFormat.format(Date(time))
    }

    suspend fun deleteHistoryData(historyData: HistoryListAdapter.SearchHistoryData) {
        searchDao.deleteHistoryData(historyData)
    }

    suspend fun addSearchedData(searchData: SearchViewLayout.SearchData) {
        val latestSearchDataItem = LatestSearchedAdapter.LatestSearchData(
             0,
            searchData.id ?: 0,
            searchData.title,
            searchData.category ?: 0,
            application.resources.getString(searchData.categoryStringId),
            searchDateFormat.format(Date(System.currentTimeMillis())),
            searchData.platform,
            searchData.platformResId,
            System.currentTimeMillis(),
            searchData.imageUrl ?: application.getString(R.string.no_image)
        )
        searchDao.addLatestSearch(latestSearchDataItem)
    }

    suspend fun clearLatestSearches() {
        searchDao.deleteAllLatestSearches()
    }

    suspend fun deleteItemFromLatestSearches(latestSearchData: LatestSearchedAdapter.LatestSearchData) {
        searchDao.deleteItemFromLatestSearches(latestSearchData)
    }
}