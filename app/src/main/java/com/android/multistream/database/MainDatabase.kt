package com.android.multistream.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.multistream.database.dao.SearchDao
import com.android.multistream.database.dao.TwitchDao
import com.android.multistream.database.entities.TopGames
import com.multistream.multistreamsearchview.recent_search.HistoryListAdapter
import com.multistream.multistreamsearchview.search_view.LatestSearchedAdapter

@Database(
    entities = [TopGames::class, HistoryListAdapter.SearchHistoryData::class, LatestSearchedAdapter.LatestSearchData::class],
    version = 3,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun twitchDao(): TwitchDao
    abstract fun searchDao(): SearchDao
}