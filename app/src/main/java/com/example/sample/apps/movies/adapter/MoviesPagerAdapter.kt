package com.example.sample.apps.movies.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sample.apps.movies.UpcomingFragment
import java.lang.IndexOutOfBoundsException

const val UPCOMING_PAGE_INDEX = 0
const val FAVOURITE_PAGE_INDEX = 1

class MoviesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentCreators: Map<Int,() -> Fragment> = mapOf(
        UPCOMING_PAGE_INDEX to { UpcomingFragment() },
        FAVOURITE_PAGE_INDEX to { UpcomingFragment() }
    )

    override fun getItemCount() = tabFragmentCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}