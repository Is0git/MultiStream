package com.iso.multistream.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_games")
data class TopGames(
    val boxArtUrl: String?, @PrimaryKey(autoGenerate = false) val id: String,
    val name: String?
)