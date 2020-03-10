package com.example.sample.apps.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sample.apps.movies.databinding.FragmentMovieDetailBinding
import com.example.sample.apps.movies.model.data.ResultsItem

/**
 * A simple [Fragment] subclass.
 */

private const val TAG = "MovieDetailFragment";
class MovieDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        binding.resultItem = arguments?.get("item") as ResultsItem
        Log.d(TAG, "onCreateView: ${binding.resultItem} ")

        return  binding.root
    }

}
