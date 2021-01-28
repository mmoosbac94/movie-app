package com.example.movieapp.overview

import androidx.lifecycle.*
import com.example.movieapp.R
import com.example.movieapp.models.MovieProperty
import com.example.movieapp.repositories.MoviesRepository
import com.google.android.play.core.internal.e
import kotlinx.coroutines.launch
import java.lang.Exception


class OverviewViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private var _recyclerViewTitle = MutableLiveData<String>()
    val recyclerViewTitle: LiveData<String>
        get() = _recyclerViewTitle

    private var _movieList = MutableLiveData<List<MovieProperty>>()
    val movieList: LiveData<List<MovieProperty>>
        get() = _movieList

    init {
        refreshDataFromRepository(R.id.popularMovies)
    }

    fun refreshDataFromRepository(movieTypeId: Int) {
        try {
            viewModelScope.launch {
                moviesRepository.refreshMovies(movieTypeId)
                getMoviesFromLocalDatabase(movieTypeId)
            }
        } catch (e: Exception) {
            throw e("Could not refresh data from repository")
        }
    }

    private fun getMoviesFromLocalDatabase(movieTypeId: Int) {
        var movieList: List<MovieProperty> = mutableListOf()
        try {
            viewModelScope.launch {
                when(movieTypeId) {
                    R.id.popularMovies -> movieList = moviesRepository.getPopularMovies()
                    R.id.topRatedMovies -> movieList = moviesRepository.getTopRatedMovies()
                }
                _movieList.value = movieList
            }
        } catch (e: Exception) {
            throw e("Could not load movies from local database")
        }
    }

    fun setRecyclerViewTitle(title: String) {
        _recyclerViewTitle.value = title
    }
}