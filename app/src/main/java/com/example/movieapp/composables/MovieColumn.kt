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
    fn: (MovieProperty) -> Unit,
    cols: Int = 2
) {
    val chunkedMovieList = movies.chunked(cols)
    LazyColumn {
        items(items = chunkedMovieList) { chunkedList ->
            Row(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                for (movie in chunkedList) {
                    if (chunkedList.size == 2) {
                        Box(Modifier.weight(100f)) {
                            MovieItem(movie = movie, fn)
                        }
                    } else {
                        Box(Modifier.weight(1f)) {
                            MovieItem(movie = movie, fn)
                        }
                    }
                    Box(Modifier.weight(1f)) {}
                }
            }
        }
    }
}