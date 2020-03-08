package com.example.sample.apps.movies

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.sample.apps.movies.adapter.MoviesListAdapter
import com.example.sample.apps.movies.databinding.FragmentUpcomingBinding
import com.example.sample.apps.movies.model.data.ResultsItem
import com.example.sample.apps.movies.utils.InjectorUtils
import com.example.sample.apps.movies.utils.errorDialog
import com.example.sample.apps.movies.viewmodels.LoadMoviesViewModel

private const val TAG = "UpcomingFragment";

class UpcomingFragment : Fragment() {
    private lateinit var binding: FragmentUpcomingBinding
    private var totalPages: Int = 0
    private val viewModel: LoadMoviesViewModel by viewModels {
        InjectorUtils.provideUpcomingMovieVM(requireContext())
    }

    private lateinit var adapter: MoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        adapter = MoviesListAdapter()
        binding.gridView.adapter = adapter

        subscribeUi(adapter)

        binding.gridView.setOnScrollListener(object: AbsListView.OnScrollListener{
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (totalItemCount > 0) {
                    val lastVisibleItem = firstVisibleItem + visibleItemCount
                    if (lastVisibleItem == totalItemCount) {
                        Log.d(TAG, "onScroll: loading more")
                        viewModel.fetchMovies()
                    }
                }
            }
            
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {


            }

        })

        return binding.root;
    }

    private fun subscribeUi(adapter: MoviesListAdapter) {
        viewModel.movieListLiveData().observe(viewLifecycleOwner,
            Observer<List<ResultsItem?>> { t -> adapter.setList(t!!,false) })

        viewModel.showErrorLiveData().observe(viewLifecycleOwner,
            Observer<String> { t -> errorDialog(activity as Context, t!!) })

        viewModel.fetchMovies()

    }


}
