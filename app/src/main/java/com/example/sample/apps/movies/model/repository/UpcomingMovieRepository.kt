package com.example.sample.apps.movies.model.repository

import android.content.Context
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.sample.apps.movies.BuildConfig
import com.example.sample.apps.movies.R
import com.example.sample.apps.movies.model.data.ResponseError
import com.example.sample.apps.movies.model.data.ResponseUpcomingMovies
import com.example.sample.apps.movies.model.data.ResultsItem
import com.example.sample.apps.movies.model.repository.db.AppDatabase
import com.example.sample.apps.movies.model.repository.db.MoviesTable
import com.example.sample.apps.movies.model.repository.network.NetworkClient
import com.example.sample.apps.movies.utils.isConnected
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class UpcomingMovieRepository(val context: Context) {

    private var movies: LiveData<List<MoviesTable>>
    init {
        val dao = AppDatabase.getInstance(context).getMoviesDao()
        movies = dao.loadMovies()
    }

    fun loadUpcomingMovies(page: Int, listener: Listener) {
        if (isConnected(context = context)) {

            val response = NetworkClient.iDef.loadUpcomingMovies(BuildConfig.APIKEY, page)
            response.enqueue(object : Callback<ResponseUpcomingMovies> {
                override fun onFailure(call: Call<ResponseUpcomingMovies>, t: Throwable) {
                    listener.loadUpcomingMoviesFailed(
                        t.message ?: context.getString(R.string.error_unknown)
                    )
                }

                override fun onResponse(
                    call: Call<ResponseUpcomingMovies>,
                    response: Response<ResponseUpcomingMovies>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result?.results.isNullOrEmpty()) {
                            listener.loadUpcomingMoviesFailed(context.getString(R.string.error_unknown))
                        } else {
                            listener.loadUpcomingMoviesSuccess(result!!)
                        }
                    } else {
                        if (response.code() === 404 || response.code() === 401) {
                            val gson = GsonBuilder().create()
                            var pojo = ResponseError()
                            try {
                                pojo = gson.fromJson(
                                    response.errorBody()!!.string(),
                                    ResponseError::class.java
                                )
                                listener.loadUpcomingMoviesFailed(pojo.statusMessage!!)
                            } catch (e: IOException) {
                                listener.loadUpcomingMoviesFailed(
                                    e.message ?: context.getString(R.string.error_unknown)
                                )
                            }
                        } else {
                            listener.loadUpcomingMoviesFailed(context.getString(R.string.error_unknown))
                        }
                    }
                }
            })

        } else {
            listener.loadUpcomingMoviesFailed(context.getString(R.string.error_network_connection))
        }

    }

    fun loadFavourites(): LiveData<List<ResultsItem>> =
        Transformations.map(movies, ::someFunc)

    private fun someFunc(dblist: List<MoviesTable>): List<ResultsItem> {
        val listResults = mutableListOf<ResultsItem>()

        dblist.forEach {  i ->
            run {
                var item = ResultsItem (
                    isFavourite = true,
                    posterPath = i.posterPath,
                    backdropPath = i.backdropPath,
                    releaseDate = i.releaseDate,
                    title = i.title,
                    id = i.movieId.toInt(),
                    overview = i.overview,
                    isTransformed = true
                )
                listResults.add(item)
            }
        }
        return listResults
    }



    interface Listener {
        fun loadUpcomingMoviesSuccess(response: ResponseUpcomingMovies)
        fun loadUpcomingMoviesFailed(error: String)
    }
}