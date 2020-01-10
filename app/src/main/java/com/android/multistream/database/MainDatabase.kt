package com.android.multistream.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.multistream.database.dao.TwitchDao
import com.android.multistream.database.entities.TopGames

@Database(entities = [TopGames::class], version = 4, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {
    abstract fun twitchDao(): TwitchDao
}