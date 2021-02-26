package com.example.movieapp.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.models.MovieProperty

@Composable
fun MovieColumn(
    movies: List<MovieProperty>,
    onItemClick: (MovieProperty) -> Unit,
    cols: Int = 2
) {
    val chunkedMovieList = movies.chunked(cols)
    LazyColumn {

        if (cols == 1) {
            items(items = movies) { movie ->
                Box(Modifier.padding(top = 50.dp, start = 50.dp, end = 50.dp)) {
                    MovieItem(movie = movie, onItemClick = onItemClick)
                }
            }
        }

        items(items = chunkedMovieList) { chunkedList ->
            Row(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                for (movie in chunkedList) {
                    if (chunkedList.size == 2) {
                        Box(Modifier.weight(1f)) {
                            MovieItem(movie = movie, onItemClick)
                        }
                    } else {
                        Box(Modifier.weight(1f)) {
                            MovieItem(movie = movie, onItemClick)
                        }

                        Box(Modifier.weight(1f)) {}
                    }
                }
            }
        }
    }
}