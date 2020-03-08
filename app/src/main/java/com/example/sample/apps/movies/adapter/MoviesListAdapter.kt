package com.example.sample.apps.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.sample.apps.movies.MoviesPagerFragment
import com.example.sample.apps.movies.MoviesPagerFragmentDirections
import com.example.sample.apps.movies.R
import com.example.sample.apps.movies.model.data.ResultsItem
import com.example.sample.apps.movies.databinding.GridMovieItemBinding
import com.example.sample.apps.movies.model.repository.db.AppDatabase
import com.example.sample.apps.movies.model.repository.db.MoviesTable
import java.util.*
import kotlin.collections.ArrayList

class MoviesListAdapter : BaseAdapter() {

    private var list = ArrayList<ResultsItem?>()

    fun setList(list: List<ResultsItem?>,clearOld: Boolean) {
        if(clearOld){
            this.list.clear()
        }
        this.list.addAll(list)
        notifyDataSetChanged()
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

            vh = ViewHolder(gridBinding)
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

    private class ViewHolder(val binding: GridMovieItemBinding) {

        fun bind(item: ResultsItem?, position: Int) {
            binding.resultItem = item
            binding.layoutCard.setOnClickListener {
                val direction =
                    MoviesPagerFragmentDirections.action_moviesPagerFragment_to_movieDetailFragment()

                it.findNavController().navigate(direction)

            }

            binding.cbFavourite.setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    item?.isFavourite = isChecked
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
                        if (isChecked) {
                            if (item?.isTransformed!!.not()) {
                                AppDatabase.getInstance(binding.root.context).getMoviesDao()
                                    .storeMovie(table)
                            }
                        } else {
                            AppDatabase.getInstance(binding.root.context).getMoviesDao()
                                .deleteMovie(table.movieId, table.title)

                        }
                    }
                    ).start()

                }
            }
        }

    }
}

