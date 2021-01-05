package com.example.movieapp.search


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.database.MovieProperty
import com.example.movieapp.repositories.MoviesRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private var _movieList = MutableLiveData<List<MovieProperty>>()
    val movieList: MutableLiveData<List<MovieProperty>> get() = _movieList


    // search movie over repository and webservice with every input letter (but only every 2 seconds or so)
    // update mutableList and consequently recyclerView


    fun getMovie(movie: String) {
        try {
            viewModelScope.launch {
                _movieList.value = moviesRepository.getSpecificMovie(movie)
            }
        } catch (e: Exception) {
            Log.i("Exception", e.toString())
        }
    }

}