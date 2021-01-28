package com.example.movieapp.repositories

import com.example.movieapp.models.MovieProperty

interface MoviesRepositoryInterface {

    suspend fun refreshMovies(movieTypeId: Int)
    suspend fun getPopularMovies(): List<MovieProperty>
    suspend fun getTopRatedMovies(): List<MovieProperty>
    suspend fun getSpecificMovie(movie: String): List<MovieProperty>

}