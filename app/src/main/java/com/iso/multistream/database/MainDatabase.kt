package com.iso.multistream.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iso.multistream.database.dao.SearchDao
import com.iso.multistream.database.dao.TwitchDao
import com.iso.multistream.database.entities.TopGames
import com.multistream.multistreamsearchview.recent_search.HistoryListAdapter
import com.multistream.multistreamsearchview.search_view.LatestSearchedAdapter

@Database(
    entities = [TopGames::class, HistoryListAdapter.SearchHistoryData::class, LatestSearchedAdapter.LatestSearchData::class],
    version = 2,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun twitchDao(): TwitchDao
    abstract fun searchDao(): SearchDao
}