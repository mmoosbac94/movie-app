package com.example.movieapp.overview

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentOverviewBinding
import org.koin.android.viewmodel.ext.android.viewModel

class OverviewFragment : Fragment() {

//    private lateinit var binding: FragmentOverviewBinding

    // Access viewModel via Koin
    private val viewModel: OverviewViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        binding = FragmentOverviewBinding.inflate(inflater)

        setHasOptionsMenu(true)

//        init()

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Scaffold(backgroundColor = Color.Black, contentColor = Color.White) {
                        OverViewScreenContent()
                    }
                }
            }
        }

//        return binding.root
    }

    @Composable
    fun OverViewScreenContent() {
        GenericMovieTerm()
        MovieColumn()
    }


    @Composable
    fun GenericMovieTerm() {
        val title by viewModel.recyclerViewTitle.observeAsState(getString(R.string.popular_movies))
        Text(text = title, Modifier.padding(start = 20.dp, bottom = 20.dp))
    }

    @Composable
    fun MovieColumn() {
        val movieList by viewModel.movieList.observeAsState(emptyList())
        LazyColumn {
            items(items = movieList) { movie ->
                Log.i("MOVIEEE", movie.title)
            }
        }
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

//    private fun init() {
//        binding.movieListRecyclerview.layoutManager = GridLayoutManager(context, 2)
//        val adapter = MovieRecyclerViewAdapter(emptyList())
//        binding.movieListRecyclerview.adapter = adapter
//        binding.title.text = getString(R.string.popular_movies)
//
//        viewModel.movieList.observe(viewLifecycleOwner) {
//            adapter.movieList = it
//            adapter.notifyDataSetChanged()
//        }
//
//        viewModel.recyclerViewTitle.observe(viewLifecycleOwner) {
//            binding.title.text = it
//        }

}