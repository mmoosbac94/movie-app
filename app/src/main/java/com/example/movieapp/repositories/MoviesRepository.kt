package com.example.movieapp.repositories

import android.util.Log
import com.example.movieapp.R
import com.example.movieapp.models.MovieProperty
import com.example.movieapp.models.MovieResult
import com.example.movieapp.models.MovieType
import com.example.movieapp.database.MoviesDatabase
import com.example.movieapp.network.MovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(private val database: MoviesDatabase) : MoviesRepositoryInterface {

    override suspend fun refreshMovies(movieTypeId: Int) {
        try {
            withContext(Dispatchers.IO) {
                val movieResult: MovieResult = when (movieTypeId) {
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
            Log.e("Exception:", e.toString())
        }
    }

    override suspend fun getPopularMovies(): List<MovieProperty> {
        val movies: List<MovieProperty>
        withContext(Dispatchers.IO) {
            movies = database.movieDao.getPopularMovies(MovieType.popular)
        }
        return movies
    }

    override suspend fun getTopRatedMovies(): List<MovieProperty> {
        val movies: List<MovieProperty>
        withContext(Dispatchers.IO) {
            movies = database.movieDao.getTopRatedMovies(MovieType.topRated)
        }
        return movies
    }

    override suspend fun getSpecificMovie(movie: String): List<MovieProperty> {
        val movies: List<MovieProperty>
        withContext(Dispatchers.IO) {
            movies =
                MovieApi.convertToDdModelWithType(MovieApi.retrofitService.getSpecificMovie(movie)).results
        }
        return movies
    }


}