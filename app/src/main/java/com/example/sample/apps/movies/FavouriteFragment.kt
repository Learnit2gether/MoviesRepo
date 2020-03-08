package com.example.sample.apps.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sample.apps.movies.databinding.FragmentUpcomingBinding

/**
 * A simple [Fragment] subclass.
 */
class FavouriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

}
