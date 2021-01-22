package com.example.movieapp.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentOverviewBinding
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private lateinit var binding: FragmentOverviewBinding

    // Access viewModel via Koin
    private val viewModel: OverviewViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOverviewBinding.inflate(inflater)

        setHasOptionsMenu(true)

        init()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchItem -> findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToSearchFragment())
            R.id.topRatedMovies -> {
                viewModel.refreshDataFromRepository(item.itemId)
                viewModel.setRecyclerViewTitle(getString(R.string.top_rated_movies))
            }
            R.id.popularMovies -> {
                viewModel.refreshDataFromRepository(item.itemId)
                viewModel.setRecyclerViewTitle(getString(R.string.popular_movies))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        binding.movieListRecyclerview.layoutManager = GridLayoutManager(context, 2)
        val adapter = MovieRecyclerViewAdapter(emptyList())
        binding.movieListRecyclerview.adapter = adapter

        viewModel.movieList.observe(viewLifecycleOwner) {
            adapter.movieList = it
            adapter.notifyDataSetChanged()
        }

        viewModel.recyclerViewTitle.observe(viewLifecycleOwner) {
            binding.title.text = it
        }

    }
}