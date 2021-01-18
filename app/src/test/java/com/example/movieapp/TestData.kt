package com.example.movieapp

import com.example.movieapp.network.MovieApiProperty

class TestData {

    companion object {
        val movieApiProperty = MovieApiProperty(
            "testId",
            "testTitle",
            "testOverview",
            "testReleaseDate",
            0.0,
            "drawable://" + R.drawable.test_image
        )
    }

}