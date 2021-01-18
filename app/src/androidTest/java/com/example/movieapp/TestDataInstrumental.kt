package com.example.movieapp

import com.example.movieapp.database.MovieProperty

class TestDataInstrumental {

    companion object {
        private val movieProperty = MovieProperty(
            "testId",
            "testTitle",
            "testOverview",
            "testReleaseDate",
            0.0,
            "drawable://" + R.drawable.test_image,
            "popular"
        )

        val listMovieProperties: List<MovieProperty> = listOf(movieProperty)
    }
}