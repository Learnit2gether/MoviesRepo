package com.example.sample.apps.movies.utils

import android.content.Context
import com.example.sample.apps.movies.model.repository.UpcomingMovieRepository
import com.example.sample.apps.movies.viewmodels.LoadMoviesViewModelFactory

object InjectorUtils {

    private fun getUpcomingMovieRepository(context: Context): UpcomingMovieRepository {
        return UpcomingMovieRepository(context)
    }

    fun provideUpcomingMovieVM(context: Context): LoadMoviesViewModelFactory{
        return LoadMoviesViewModelFactory(getUpcomingMovieRepository(context))
    }

}