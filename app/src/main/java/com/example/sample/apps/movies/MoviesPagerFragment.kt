package com.example.sample.apps.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.sample.apps.movies.adapter.FAVOURITE_PAGE_INDEX
import com.example.sample.apps.movies.adapter.MoviesPagerAdapter
import com.example.sample.apps.movies.adapter.UPCOMING_PAGE_INDEX
import com.example.sample.apps.movies.databinding.FragmentMoviesPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * A simple [Fragment] subclass.
 */
class MoviesPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesPagerBinding.inflate(inflater,container, false)

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = MoviesPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            UPCOMING_PAGE_INDEX -> R.drawable.movie_list_tab_selector
            FAVOURITE_PAGE_INDEX -> R.drawable.favourite_list_stab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            UPCOMING_PAGE_INDEX -> getString(R.string.upcoming_movies)
            FAVOURITE_PAGE_INDEX -> getString(R.string.favourite_movies)
            else -> null
        }
    }

}
