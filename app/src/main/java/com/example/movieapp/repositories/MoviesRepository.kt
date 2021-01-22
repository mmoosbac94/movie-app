package com.example.movieapp.repositories

import android.util.Log
import com.example.movieapp.R
import com.example.movieapp.database.MovieProperty
import com.example.movieapp.database.MovieResult
import com.example.movieapp.database.MovieType
import com.example.movieapp.database.MoviesDatabase
import com.example.movieapp.network.MovieApi
import com.google.android.play.core.internal.e
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class MoviesRepository(private val database: MoviesDatabase) {

    open suspend fun refreshMovies(itemId: Int) {
        try {
            withContext(Dispatchers.IO) {
                val movieResult: MovieResult = when (itemId) {
                    R.id.popularMovies -> MovieApi.convertToDdModelWithType(
                        MovieApi.retrofitService.getPopularMovies(),
                        MovieType.popular
                    )
                    R.id.topRatedMovies -> MovieApi.convertToDdModelWithType(
                        MovieApi.retrofitService.getTopRatedMovies(),
                        MovieType.topRated
                    )
                    else -> MovieApi.convertToDdModelWithType(
                        MovieApi.retrofitService.getTopRatedMovies(),
                        MovieType.topRated
                    )
                }
                database.movieDao.insertAll(movieResult.results)
            }
        } catch (e: Exception) {
            throw e("Refresh movies from repository failed")
        }
    }

    open suspend fun getPopularMovies(): List<MovieProperty> {
        val movies: List<MovieProperty>
        withContext(Dispatchers.IO) {
            movies = database.movieDao.getPopularMovies(MovieType.popular)
        }
        return movies
    }

    suspend fun getTopRatedMovies(): List<MovieProperty> {
        val movies: List<MovieProperty>
        withContext(Dispatchers.IO) {
            movies = database.movieDao.getTopRatedMovies(MovieType.topRated)
        }
        return movies
    }

    suspend fun getSpecificMovie(movie: String): List<MovieProperty> {
        val movies: List<MovieProperty>
        withContext(Dispatchers.IO) {
            movies =
                MovieApi.convertToDdModelWithType(MovieApi.retrofitService.getSpecificMovie(movie)).results
        }
        return movies
    }


}