package com.example.movieapp

import com.example.movieapp.database.MovieResult
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
        val movieApiProperty = MovieApiProperty(
            "testId",
            "testTitle",
            "testOverview",
            "testReleaseDate",
            0.0,
            "testImagePath"
        )
        movieApiPropertyList = listOf(movieApiProperty)
        movieApiResult = MovieApiResult(movieApiPropertyList)
    }

    @Test
    fun check_if_convertToDBModelWithDefaultType_works() {
        val movieResult: MovieResult = MovieApi.convertToDdModelWithType(movieApiResult)
        assertEquals("default", movieResult.results[0].type)
    }

    @Test
    fun check_if_convertToDBModelWithPopularType_works() {
        val movieResult: MovieResult = MovieApi.convertToDdModelWithType(movieApiResult, "popular")
        assertEquals("popular", movieResult.results[0].type)
    }


}