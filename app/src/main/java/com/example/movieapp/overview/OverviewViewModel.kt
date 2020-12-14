package com.example.movieapp.overview

import android.util.Log
import androidx.lifecycle.*
import com.example.movieapp.database.MovieProperty
import com.example.movieapp.repositories.MoviesRepository
import kotlinx.coroutines.launch
import java.lang.Exception


class OverviewViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private var _movieList = MutableLiveData<List<MovieProperty>>()
    val movieList: LiveData<List<MovieProperty>>
        get() = _movieList

    init {
        refreshDataFromRepository()
        getMoviesFromLocalDatabase()
    }

    private fun refreshDataFromRepository() {
        try {
            viewModelScope.launch {
                moviesRepository.refreshMovies()
            }
        } catch (e: Exception) {
            Log.i("EXCEPTION", e.toString())
        }
    }

    private fun getMoviesFromLocalDatabase() {
        try {
            viewModelScope.launch {
                val movieList = moviesRepository.getMovies()
                _movieList.value = movieList
            }
        } catch (e: Exception) {
            Log.i("EXCEPTION", e.toString())
        }
    }
}