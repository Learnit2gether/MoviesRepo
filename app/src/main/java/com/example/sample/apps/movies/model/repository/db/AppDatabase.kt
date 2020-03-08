package com.example.sample.apps.movies.model.repository.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sample.apps.movies.utils.DATABASE_NAME
import androidx.room.Database

@Database(entities = [MoviesTable::class],version = 1,exportSchema = false)
abstract class AppDatabase:  RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDAO

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}