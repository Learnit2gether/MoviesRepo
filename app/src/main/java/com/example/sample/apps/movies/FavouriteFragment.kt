package com.example.sample.apps.movies

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.sample.apps.movies.adapter.MoviesListAdapter
import com.example.sample.apps.movies.databinding.FragmentUpcomingBinding
import com.example.sample.apps.movies.model.data.ResultsItem
import com.example.sample.apps.movies.utils.InjectorUtils
import com.example.sample.apps.movies.utils.errorDialog
import com.example.sample.apps.movies.viewmodels.LoadMoviesViewModel

/**
 * A simple [Fragment] subclass.
 */

private const val TAG = "FavouriteFragment";

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingBinding

    private val viewModel: LoadMoviesViewModel by viewModels {
        InjectorUtils.provideUpcomingMovieVM(requireContext())
    }

    private lateinit var adapter: MoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        adapter = MoviesListAdapter()
        binding.gridView.adapter = adapter

        subscribeUi(adapter)

        return binding.root
    }


    private fun subscribeUi(adapter: MoviesListAdapter) {

        viewModel.loadFavourites().observe(viewLifecycleOwner, Observer<List<ResultsItem>> { t ->
            run {
                Log.d(TAG, "subscribeUi: transformed")
                adapter.setList(t!!, true)
            }
        })

    }

}
