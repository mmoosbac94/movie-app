package com.example.movieapp.repositories

import android.util.Log
import com.example.movieapp.database.MovieProperty
import com.example.movieapp.database.MoviesDatabase
import com.example.movieapp.network.MovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(private val database: MoviesDatabase) {

    suspend fun refreshMovies(url: String) {
        try {
            withContext(Dispatchers.IO) {
                val movieResult = MovieApi.retrofitService.getProperties(url)
                database.movieDao.insertAll(movieResult.results)
            }
        } catch (e: Exception) {
            Log.i("EXCEPTION", e.toString())
        }
    }

    suspend fun getMovies(): List<MovieProperty> {
        val movies: List<MovieProperty>
        withContext(Dispatchers.IO) {
            movies = database.movieDao.getMovies()
        }
        return movies
    }


}