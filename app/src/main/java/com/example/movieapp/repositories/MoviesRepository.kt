package com.example.movieapp.repositories

import android.util.Log
import com.example.movieapp.R
import com.example.movieapp.database.MovieProperty
import com.example.movieapp.database.MovieResult
import com.example.movieapp.database.MoviesDatabase
import com.example.movieapp.network.MovieApi
import com.example.movieapp.network.MovieApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(private val database: MoviesDatabase) {

    suspend fun refreshMovies(itemId: Int) {
        try {
            withContext(Dispatchers.IO) {
                val movieResult: MovieResult = when (itemId) {
                    R.id.popularMovies -> MovieApi.convertToDdModelWithType(MovieApi.retrofitService.getPopularMovies(), "popular")
                    R.id.topRatedMovies -> MovieApi.convertToDdModelWithType(MovieApi.retrofitService.getTopRatedMovies(), "topRated")
                    else -> MovieApi.convertToDdModelWithType(MovieApi.retrofitService.getTopRatedMovies(), "topRated")
                }
                database.movieDao.insertAll(movieResult.results)
            }
        } catch (e: Exception) {
            Log.i("EXCEPTION", e.toString())
        }
    }

    suspend fun getPopularMovies(): List<MovieProperty> {
        val movies: List<MovieProperty>
        withContext(Dispatchers.IO) {
            movies = database.movieDao.getPopularMovies("popular")
        }
        return movies
    }

    suspend fun getTopRatedMovies(): List<MovieProperty> {
        Log.i("TOOOP", "TOOOP")
        val movies: List<MovieProperty>
        withContext(Dispatchers.IO) {
            movies = database.movieDao.getTopRatedMovies("topRated")
        }
        return movies
    }


}