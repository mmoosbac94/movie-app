package com.example.movieapp.search


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.MovieProperty
import com.example.movieapp.repositories.MoviesRepository
import com.google.android.play.core.internal.e
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private var _movieList = MutableLiveData<List<MovieProperty>>()
    val movieList: MutableLiveData<List<MovieProperty>> get() = _movieList

    fun getMovie(movie: String) {
        try {
            viewModelScope.launch {
                _movieList.value = moviesRepository.getSpecificMovie(movie)
            }
        } catch (e: Exception) {
            throw e("Could not get specific movie")
        }
    }

}