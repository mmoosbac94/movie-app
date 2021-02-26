package com.example.movieapp.overview

import android.os.Bundle
import android.view.*
import androidx.compose.foundation.layout.*
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
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.composables.MovieColumn
import com.example.movieapp.models.MovieProperty
import org.koin.android.viewmodel.ext.android.viewModel


class OverviewFragment : Fragment() {

    // Access viewModel via Koin
    private val viewModel: OverviewViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        init()

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Scaffold(backgroundColor = Color.Black, contentColor = Color.White) {
                        OverViewScreenContent()
                    }
                }
            }
        }
    }


    private fun init() {
        if (viewModel.movieList.value?.isEmpty() == true) {
            viewModel.genericMovieTitle = getString(R.string.popular_movies)
            viewModel.refreshDataFromRepository(R.id.popularMovies)
        }
    }

    @Composable
    fun OverViewScreenContent() {

        val movieList by viewModel.movieList.observeAsState(emptyList())
        val genericMovieTitle = viewModel.genericMovieTitle

        val onItemClick = { movie: MovieProperty ->
            requireParentFragment().findNavController().navigate(
                OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(movie)
            )
        }

        Column {
            GenericMovieTerm(genericMovieTitle)
            MovieColumn(movieList, onItemClick)
        }
    }


    @Composable
    fun GenericMovieTerm(genericMovieTitle: String) {
        Text(text = genericMovieTitle, Modifier.padding(start = 20.dp, bottom = 20.dp))
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
                viewModel.genericMovieTitle = (getString(R.string.top_rated_movies))
            }
            R.id.popularMovies -> {
                viewModel.refreshDataFromRepository(item.itemId)
                viewModel.genericMovieTitle = (getString(R.string.popular_movies))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}