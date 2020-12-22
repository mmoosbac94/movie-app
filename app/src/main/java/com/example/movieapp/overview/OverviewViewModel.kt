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
        refreshDataFromRepository()
    }

    fun refreshDataFromRepository(itemId: Int = 0) {
        var url = ""
        when (itemId) {
            0 -> url =
                "3/movie/popular?api_key=0c97571ddf07813f8e4e1712ab264a77&language=en-US&page=1"
            R.id.otherMovies -> url =
                "3/movie/top_rated?api_key=0c97571ddf07813f8e4e1712ab264a77&language=en-US&page=1"
        }
        try {
            viewModelScope.launch {
                moviesRepository.refreshMovies(url)
            }
            getMoviesFromLocalDatabase()
        } catch (e: Exception) {
            Log.i("EXCEPTION", e.toString())
        }
    }

    private fun getMoviesFromLocalDatabase() {
        try {
            viewModelScope.launch {
                val movieList = moviesRepository.getMovies()
                Log.i("BUTT", "BUTT")
                _movieList.value = movieList
            }
        } catch (e: Exception) {
            Log.i("EXCEPTION", e.toString())
        }
    }
}