package com.example.movieapp.overview

import android.util.Log
import androidx.lifecycle.*
import com.example.movieapp.R
import com.example.movieapp.database.MovieProperty
import com.example.movieapp.repositories.MoviesRepository
import kotlinx.coroutines.launch
import java.lang.Exception


class OverviewViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private var _movieList = MutableLiveData<List<MovieProperty>>()
    val movieList: LiveData<List<MovieProperty>>
        get() = _movieList

    init {
        refreshDataFromRepository(R.id.popularMovies)
    }

    fun refreshDataFromRepository(itemId: Int) {
        try {
            viewModelScope.launch {
                moviesRepository.refreshMovies(itemId)
                getMoviesFromLocalDatabase(itemId)
            }
        } catch (e: Exception) {
            Log.i("EXCEPTION", e.toString())
        }
    }

    private fun getMoviesFromLocalDatabase(itemId: Int) {
        var movieList: List<MovieProperty> = mutableListOf()
        try {
            viewModelScope.launch {
                when(itemId) {
                    R.id.popularMovies -> movieList = moviesRepository.getPopularMovies()
                    R.id.topRatedMovies -> movieList = moviesRepository.getTopRatedMovies()
                }
                _movieList.value = movieList
            }
        } catch (e: Exception) {
            Log.i("EXCEPTION", e.toString())
        }
    }


    fun checkTitle(itemId: Int) {

    }
}