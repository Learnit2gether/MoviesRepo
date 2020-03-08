package com.example.sample.apps.movies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sample.apps.movies.model.repository.UpcomingMovieRepository

class LoadMoviesViewModelFactory(
    private val repository: UpcomingMovieRepository
) : ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoadMoviesViewModel(repository) as T
    }

}