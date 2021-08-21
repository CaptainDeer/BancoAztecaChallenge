package com.captaindeer.bancoaztecachallenge.data.local.dao

import android.icu.text.Replaceable
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.captaindeer.bancoaztecachallenge.data.local.entities.MovieEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieEntity: MovieEntity)

    @Query("select * from movies")
    fun getTopMovies(): MutableList<MovieEntity>

}