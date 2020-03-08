package com.example.sample.apps.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.sample.apps.movies.R
import com.example.sample.apps.movies.model.data.ResultsItem
import com.example.sample.apps.movies.databinding.GridMovieItemBinding

class MoviesListAdapter:  BaseAdapter(){
    private var list = ArrayList<ResultsItem?>()

    fun setList(list: List<ResultsItem?>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: ViewHolder
        val root: View
        val gridBinding: GridMovieItemBinding

        if(convertView == null){
            gridBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), R.layout.grid_movie_item,parent,false)
            root = gridBinding.root

            vh = ViewHolder(gridBinding)
            root.tag = vh
        }else{
            root = convertView
            vh = root.tag as ViewHolder
        }
        vh.bind(list.get(position))

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

    private class ViewHolder(val binding: GridMovieItemBinding){

        fun bind(item: ResultsItem?){
            binding.resultItem = item
        }

    }
}

