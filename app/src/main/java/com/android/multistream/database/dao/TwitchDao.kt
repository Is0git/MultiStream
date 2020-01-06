package com.android.multistream.database.dao

import androidx.paging.DataSource
import androidx.room.*
import com.android.multistream.database.entities.TopGames


@Dao
interface TwitchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopGames(topGames: List<TopGames>)

    @Query("SELECT * from top_games")
    fun getTopGames(): DataSource.Factory<Int, TopGames>

    @Query("DELETE FROM top_games")
    suspend fun deleteAllFromTopGames()
}