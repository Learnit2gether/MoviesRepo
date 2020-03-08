package com.example.sample.apps.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.sample.apps.movies.databinding.ActivityMoviesBinding
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient


class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Stetho.initializeWithDefaults(this);
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        DataBindingUtil.setContentView<ActivityMoviesBinding>(this,R.layout.activity_movies)

    }

}
