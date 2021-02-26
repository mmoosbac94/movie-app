package com.example.movieapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.R
import com.example.movieapp.models.MovieProperty
import com.example.movieapp.utils.DEFAULT_RECIPE_IMAGE
import com.example.movieapp.utils.MOVIE_BASE_URL
import com.example.movieapp.utils.loadPicture


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {

            val args = DetailFragmentArgs.fromBundle(requireArguments())

            setContent {
                MaterialTheme {
                    Scaffold(backgroundColor = Color.Black, contentColor = Color.White) {
                        DetailsScreenContent(args.movie)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsScreenContent(movie: MovieProperty) {

    val image =
        loadPicture(movieImageURL = movie.movieImg, defaultImage = DEFAULT_RECIPE_IMAGE).value

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            image?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "MovieImg",
                    modifier = Modifier.width(220.dp).padding(top = 30.dp, bottom = 40.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = movie.title,
                style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center)
            )
            Text(
                text = LocalContext.current.getString(
                    R.string.voteAverage,
                    movie.voteAverage.toString()
                ),
                modifier = Modifier.padding(10.dp)
            )
            Text(text = movie.releaseDate)
            Text(
                text = movie.overview,
                style = TextStyle(textAlign = TextAlign.Center),
                modifier = Modifier.padding(30.dp)
            )
        }
    }
}