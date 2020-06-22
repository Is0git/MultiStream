package com.iso.multistream.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.multistream.multistreamsearchview.recent_search.HistoryListAdapter
import com.multistream.multistreamsearchview.search_view.LatestSearchedAdapter

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_history_table ORDER BY time DESC")
    fun getSearchesHistory(): LiveData<MutableList<HistoryListAdapter.SearchHistoryData>>

    @Query("SELECT * FROM latest_searches_table ORDER BY time DESC")
    fun getLatestSearches(): LiveData<MutableList<LatestSearchedAdapter.LatestSearchData>>

    @Insert
    suspend fun addHistoryData(searchHistoryData: HistoryListAdapter.SearchHistoryData)

    @Delete
    suspend fun deleteHistoryData(searchHistoryData: HistoryListAdapter.SearchHistoryData)

    @Insert
    suspend fun addLatestSearch(latestSearch: LatestSearchedAdapter.LatestSearchData)

    @Query("DELETE FROM latest_searches_table")
    suspend fun deleteAllLatestSearches()

    @Delete
    suspend fun deleteItemFromLatestSearches(latestSearch: LatestSearchedAdapter.LatestSearchData)
}