package com.example.sample.apps.movies

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.sample.apps.movies.adapter.FAVOURITE_PAGE_INDEX
import com.example.sample.apps.movies.adapter.MoviesPagerAdapter
import com.example.sample.apps.movies.adapter.UPCOMING_PAGE_INDEX
import com.example.sample.apps.movies.databinding.FragmentMoviesPagerBinding
import com.google.android.material.tabs.TabLayoutMediator


/**
 * A simple [Fragment] subclass.
 */

private const val TAG = "MoviesPagerFragment";
class MoviesPagerFragment : Fragment() {

    lateinit var queryTextListener: SearchView.OnQueryTextListener
    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesPagerBinding.inflate(inflater,container, false)

        val tabLayout = binding.tabs
        viewPager = binding.viewPager

        viewPager.adapter = MoviesPagerAdapter(this)
        viewPager.offscreenPageLimit = 2

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        viewPager.currentItem = 0

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    Log.i("onQueryTextChange", newText)
                    viewPager.currentItem = 0
                    UpcomingFragment.adapter.filter.filter(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.i("onQueryTextSubmit", query)
                    viewPager.currentItem = 0
                    UpcomingFragment.adapter.filter.filter(query)

                    return true
                }
            }
            searchView.setOnQueryTextListener(queryTextListener)
        }



        return super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "on Resume: ")
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
