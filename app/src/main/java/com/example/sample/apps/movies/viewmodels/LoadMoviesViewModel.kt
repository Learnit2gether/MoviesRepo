package com.example.sample.apps.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sample.apps.movies.model.data.ResponseUpcomingMovies
import com.example.sample.apps.movies.model.data.ResultsItem
import com.example.sample.apps.movies.model.repository.UpcomingMovieRepository

class LoadMoviesViewModel internal constructor(val repository: UpcomingMovieRepository): ViewModel(){

    private var movieList = MutableLiveData<List<ResultsItem?>>()
    private var showError = MutableLiveData<String>()

    fun fetchMovies(page: Int){

        repository.loadUpcomingMovies(1,object: UpcomingMovieRepository.Listener{
            override fun loadUpcomingMoviesSuccess(response: ResponseUpcomingMovies) {
                movieList.value = response.results
            }

            override fun loadUpcomingMoviesFailed(error: String) {
                showError.value = error
            }
        })
    }

    fun movieListLiveData(): LiveData<List<ResultsItem?>> = movieList
    fun showErrorLiveData(): LiveData<String> = showError

}