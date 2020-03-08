package com.example.sample.apps.movies.model.repository.network

import com.example.sample.apps.movies.model.data.ResponseUpcomingMovies
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IDefinition {


    @GET("upcoming")
    fun loadUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<ResponseUpcomingMovies>

}