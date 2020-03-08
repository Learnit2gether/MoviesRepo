package com.example.sample.apps.movies.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sample.apps.movies.model.data.ResponseUpcomingMovies
import com.example.sample.apps.movies.model.data.ResultsItem
import com.example.sample.apps.movies.model.repository.UpcomingMovieRepository

private const val TAG = "LoadMoviesViewModel";

class LoadMoviesViewModel internal constructor(val repository: UpcomingMovieRepository): ViewModel(){

    private var movieList = MutableLiveData<List<ResultsItem?>>()
    private var showError = MutableLiveData<String>()
    private var totalPages: Int? = 1
    private var count = 0
    private var loadingFinish = MutableLiveData<Boolean>()


    fun fetchMovies(){
        if(totalPages?:1 > count){
            count++
        }else{
            return
        }

        repository.loadUpcomingMovies(count,object: UpcomingMovieRepository.Listener{
            override fun loadUpcomingMoviesSuccess(response: ResponseUpcomingMovies) {
                totalPages = response.totalPages
                Log.d(TAG, "loadUpcomingMoviesSuccess: ${totalPages}")
                movieList.value = response.results
            }

            override fun loadUpcomingMoviesFailed(error: String) {
                showError.value = error
            }
        })

    }

    fun movieListLiveData(): LiveData<List<ResultsItem?>> = movieList
    fun showErrorLiveData(): LiveData<String> = showError
    fun loadFavourites(): LiveData<List<ResultsItem>> = repository.loadFavourites()

}