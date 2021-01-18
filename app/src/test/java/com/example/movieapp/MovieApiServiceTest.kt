package com.example.movieapp

import com.example.movieapp.database.MovieResult
import com.example.movieapp.database.MovieType
import com.example.movieapp.network.MovieApi
import com.example.movieapp.network.MovieApiProperty
import com.example.movieapp.network.MovieApiResult
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieApiServiceTest {

    private lateinit var movieApiPropertyList: List<MovieApiProperty>
    private lateinit var movieApiResult: MovieApiResult

    @Before
    fun initialize() {
        movieApiPropertyList = listOf(TestData.movieApiProperty)
        movieApiResult = MovieApiResult(movieApiPropertyList)
    }

    @Test
    fun check_if_convertToDBModelWithDefaultType_works() {
        val movieResult: MovieResult = MovieApi.convertToDdModelWithType(movieApiResult)
        assertEquals(MovieType.default, movieResult.results[0].type)
    }

    @Test
    fun check_if_convertToDBModelWithPopularType_works() {
        val movieResult: MovieResult =
            MovieApi.convertToDdModelWithType(movieApiResult, MovieType.popular)
        assertEquals(MovieType.popular, movieResult.results[0].type)
    }


}