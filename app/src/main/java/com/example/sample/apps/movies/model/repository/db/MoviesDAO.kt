package com.example.sample.apps.movies.model.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoviesDAO {

    @Insert
    fun storeMovie(item: MoviesTable): Long

    @Query("DELETE FROM movies WHERE movieId = :id or title= :name")
    fun deleteMovie(id: String?,name: String?): Int

    @Query("SELECT COUNT(title) from movies where title =:name")
    fun countMovies(name: String): Int

    @Query("SELECT * FROM movies")
    fun loadMovies(): LiveData<List<MoviesTable>>

}