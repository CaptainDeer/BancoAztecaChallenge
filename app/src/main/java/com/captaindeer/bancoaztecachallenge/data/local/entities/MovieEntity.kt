package com.captaindeer.bancoaztecachallenge.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")val id: Int,
    @ColumnInfo(name = "original_languaje")val original_languaje: String,
    @ColumnInfo(name = "overview")val overview: String,
    @ColumnInfo(name = "poster_path")val poster_path: String,
    @ColumnInfo(name = "title")val title: String,
    @ColumnInfo(name = "vote_average")val vote_average: Float
    )