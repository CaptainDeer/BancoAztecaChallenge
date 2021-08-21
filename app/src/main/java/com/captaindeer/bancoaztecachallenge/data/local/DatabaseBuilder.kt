package com.captaindeer.bancoaztecachallenge.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.captaindeer.bancoaztecachallenge.data.local.dao.MoviesDao
import com.captaindeer.bancoaztecachallenge.data.local.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class DatabaseBuilder : RoomDatabase() {

    abstract fun MoviesDao(): MoviesDao

    companion object {
        @Volatile
        private var instance: DatabaseBuilder? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, DatabaseBuilder::class.java, "TheMovieDatabase.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }


}