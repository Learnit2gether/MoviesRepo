package com.example.sample.apps.movies.model.repository.network

import android.content.Context
import com.example.sample.apps.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

        val iDef : IDefinition by lazy {

        val interceptor = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }else{
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        return@lazy retrofit.create(IDefinition::class.java)
    }


}