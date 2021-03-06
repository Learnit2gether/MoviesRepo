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
    companion object {
        lateinit var adapter: MoviesListAdapter
    }

    private val viewModel: LoadMoviesViewModel by viewModels {
        InjectorUtils.provideUpcomingMovieVM(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "onCreateView: ")
        binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        adapter = MoviesListAdapter()
        adapter.setUpcoming(true)
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
            Observer<List<ResultsItem?>> { t -> adapter.setList(t!!,true) })

        viewModel.showErrorLiveData().observe(viewLifecycleOwner,
            Observer<String> { t -> errorDialog(activity as Context, t!!) })

        viewModel.fetchMovies()

    }


}
