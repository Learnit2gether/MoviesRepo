package com.example.sample.apps.movies

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.sample.apps.movies.adapter.MoviesListAdapter
import com.example.sample.apps.movies.databinding.FragmentUpcomingBinding
import com.example.sample.apps.movies.model.data.ResultsItem
import com.example.sample.apps.movies.utils.InjectorUtils
import com.example.sample.apps.movies.utils.errorDialog
import com.example.sample.apps.movies.viewmodels.LoadMoviesViewModel

class UpcomingFragment : Fragment() {

    private val viewModel: LoadMoviesViewModel by viewModels {
        InjectorUtils.provideUpcomingMovieVM(requireContext())
    }

    private lateinit var adapter: MoviesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentUpcomingBinding =
            FragmentUpcomingBinding.inflate(inflater, container, false)

        adapter = MoviesListAdapter()
        binding.gridView.adapter = adapter

        subscribeUi(adapter)

        return binding.root;
    }

    private fun subscribeUi(adapter: MoviesListAdapter) {
        viewModel.movieListLiveData().observe(viewLifecycleOwner,
            Observer<List<ResultsItem?>> { t -> adapter.setList(t!!) })

        viewModel.showErrorLiveData().observe(viewLifecycleOwner,
            Observer<String> { t -> errorDialog(activity as Context, t!!) })

        viewModel.fetchMovies(1)

    }


}
