package com.example.movieapp.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentOverviewBinding
import org.koin.android.viewmodel.ext.android.getViewModel

class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOverviewBinding.inflate(inflater)

        init()

        return binding.root
    }

    private fun init() {

        binding.movieListRecyclerview.layoutManager = GridLayoutManager(context, 2)
        val adapter = MovieRecyclerViewAdapter(emptyList())
        binding.movieListRecyclerview.adapter = adapter

        // Access viewModel via Koin
        getViewModel<OverviewViewModel>().movieList.observe(viewLifecycleOwner, Observer {

            adapter.movieList = it
            adapter.notifyDataSetChanged()
        })

    }
}