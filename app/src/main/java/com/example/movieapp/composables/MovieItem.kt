package com.example.movieapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.R
import com.example.movieapp.models.MovieProperty
import com.example.movieapp.utils.loadPicture


@Composable
fun MovieItem(movie: MovieProperty, onItemClick: (MovieProperty) -> Unit) {

    val image =
        loadPicture(movie).value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {
        image?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "MovieImg",
                modifier = Modifier.fillMaxWidth().clickable {
                    onItemClick(movie)
                },
            )
        }
        Text(
            text = movie.title,
            modifier = Modifier.padding(top = 7.dp),
            style = TextStyle(
                color = Color.White,
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
                color = Color.White,
                fontSize = 14.sp
            )
        )
    }

}
