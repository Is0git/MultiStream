package com.iso.multistream.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iso.multistream.database.entities.TopGames


@Dao
interface TwitchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopGames(topGames: List<TopGames>)

    @Query("SELECT * from top_games")
    fun getTopGames(): DataSource.Factory<Int, TopGames>

    @Query("DELETE FROM top_games")
    suspend fun deleteAllFromTopGames()
}