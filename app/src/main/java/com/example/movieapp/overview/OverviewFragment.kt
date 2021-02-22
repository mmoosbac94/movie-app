package com.example.movieapp.overview

import android.os.Bundle
import android.view.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.models.MovieProperty
import com.example.movieapp.utils.DEFAULT_RECIPE_IMAGE
import com.example.movieapp.utils.MOVIE_BASE_URL
import com.example.movieapp.utils.loadPicture
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
        viewModel.genericMovieTitle = getString(R.string.popular_movies)
        viewModel.refreshDataFromRepository(R.id.popularMovies)
    }

    @Composable
    fun OverViewScreenContent() {

        val movieList by viewModel.movieList.observeAsState(emptyList())
        val genericMovieTitle = viewModel.genericMovieTitle

        Column {
            GenericMovieTerm(genericMovieTitle)
            MovieColumn(movieList)
        }
    }


    @Composable
    fun GenericMovieTerm(genericMovieTitle: String) {
        Text(text = genericMovieTitle, Modifier.padding(start = 20.dp, bottom = 20.dp))
    }

    @Composable
    fun MovieColumn(movies: List<MovieProperty>, cols: Int = 2) {
        val chunkedMovieList = movies.chunked(cols)
        LazyColumn {
            items(items = chunkedMovieList) { chunkedList ->
                Row(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                    for (movie in chunkedList) {
                        if (chunkedList.size == 2) {
                            Box(Modifier.weight(1f)) {
                                MovieItem(movie = movie)
                            }
                        } else {
                            Box(Modifier.weight(1f)) {
                                MovieItem(movie = movie)
                            }
                            Box(Modifier.weight(1f)) {}
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun MovieItem(movie: MovieProperty) {

        val movieUrl = MOVIE_BASE_URL + movie.movieImg

        val image =
            loadPicture(url = movieUrl, defaultImage = DEFAULT_RECIPE_IMAGE).value

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            image?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "MovieImg",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = movie.title,
                modifier = Modifier.padding(top = 7.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            )
            Text(
                text = LocalContext.current.getString(
                    R.string.voteAverage,
                    movie.voteAverage.toString()
                ),
                modifier = Modifier.padding(top = 3.dp),
                style = TextStyle(
                    fontSize = 14.sp
                )
            )
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