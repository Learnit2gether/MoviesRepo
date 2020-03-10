package com.example.sample.apps.movies.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.sample.apps.movies.MoviesPagerFragmentDirections
import com.example.sample.apps.movies.R
import com.example.sample.apps.movies.databinding.GridMovieItemBinding
import com.example.sample.apps.movies.model.data.ResultsItem
import com.example.sample.apps.movies.model.repository.db.AppDatabase
import com.example.sample.apps.movies.model.repository.db.MoviesTable
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "MoviesListAdapter";

class MoviesListAdapter : BaseAdapter() {

    private var list = ArrayList<ResultsItem?>()
    var isFromUpcoming = false

    fun setList(list: List<ResultsItem?>, clearOld: Boolean) {
        if (clearOld) {
            this.list.clear()
        }
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setUpcoming(isFromUpcoming: Boolean) {
        this.isFromUpcoming = isFromUpcoming
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: ViewHolder
        val root: View
        val gridBinding: GridMovieItemBinding

        if (convertView == null) {
            gridBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                R.layout.grid_movie_item,
                parent,
                false
            )
            root = gridBinding.root

            vh = ViewHolder(gridBinding, isFromUpcoming)
            root.tag = vh
        } else {
            root = convertView
            vh = root.tag as ViewHolder
        }
        vh.bind(list.get(position), position)

        return root
    }

    override fun getItem(position: Int): Any {
        return list.get(position) ?: ResultsItem()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return list.size
    }

    private class ViewHolder(val binding: GridMovieItemBinding, val isFromUpcoming: Boolean) {

        fun bind(item: ResultsItem?, position: Int) {
            binding.resultItem = item

            binding.layoutCard.setOnClickListener {
                var direction =
                    MoviesPagerFragmentDirections.actionMoviesPagerFragmentToMovieDetailFragment(
                        item!!
                    )
                it.findNavController().navigate(direction)
            }


            binding.cbFavourite.setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    val table = MoviesTable(
                        UUID.randomUUID().toString(),
                        item?.id.toString(),
                        item?.posterPath,
                        item?.backdropPath,
                        item?.title,
                        item?.overview,
                        item?.releaseDate
                    )

                    Thread(Runnable {
                        synchronized(this@ViewHolder) {
                            if (isChecked) {
                                Log.d(TAG, "bind: movies ${item}")
                                item?.let {
                                    if (it.isTransformed!!.not()) {
                                        val db = AppDatabase.getInstance(binding.root.context)
                                            .getMoviesDao()
                                        if (db.countMovies(item?.title!!) == 0) {
                                            db.storeMovie(table)
                                        }
                                        binding.cbFavourite.postDelayed(Runnable {
                                            binding.cbFavourite.isChecked = false
                                            Toast.makeText(
                                                binding.root.context,
                                                "${item.title} Added to favourite",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }, 500)
                                    }

                                }
                            } else {
                                if (isFromUpcoming.not()) {
                                    AppDatabase.getInstance(binding.root.context).getMoviesDao()
                                        .deleteMovie(table.movieId, table.title)
                                    binding.cbFavourite.postDelayed(Runnable {
                                        Toast.makeText(
                                            binding.root.context,
                                            "${item?.title!!} removed from favourite",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }, 500)
                                } else {}
                            }
                        }
                    }
                    ).start()
                }
            }
        }
    }
}

