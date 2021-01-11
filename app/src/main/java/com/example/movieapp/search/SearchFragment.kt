package com.example.movieapp.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.databinding.SearchFragmentBinding
import com.example.movieapp.overview.MovieRecyclerViewAdapter
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {


    private lateinit var binding: SearchFragmentBinding
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater)

        init()

        return binding.root
    }

    private fun init() {
        binding.movieListRecyclerview.layoutManager = GridLayoutManager(context, 1)
        val adapter = MovieRecyclerViewAdapter(emptyList())
        binding.movieListRecyclerview.adapter = adapter

        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            adapter.movieList = it
            adapter.notifyDataSetChanged()
        })

        binding.movieSearchInput.doAfterTextChanged {
            if (it.toString().isNotEmpty()) viewModel.getMovie(it.toString())
        }

    }

}