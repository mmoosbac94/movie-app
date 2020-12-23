package com.example.movieapp.network

import android.util.Log
import com.example.movieapp.database.MovieProperty
import com.example.movieapp.database.MovieResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

private const val BASE_URL = "https://api.themoviedb.org/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface MovieApiService {
    @GET("3/movie/popular?api_key=0c97571ddf07813f8e4e1712ab264a77&language=en-US&page=1")
    suspend fun getPopularMovies(): MovieApiResult

    @GET("3/movie/top_rated?api_key=0c97571ddf07813f8e4e1712ab264a77&language=en-US&page=1")
    suspend fun getTopRatedMovies(): MovieApiResult
}

object MovieApi {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }

    fun convertToDdModelWithType(movieApiResult: MovieApiResult, type: String): MovieResult {

        val list: MutableList<MovieProperty> = mutableListOf()

        movieApiResult.result.map {
            val movieProperty: MovieProperty = MovieProperty(
                id = it.id,
                title = it.title,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
                movieImg = it.movieImg,
                type = type
            )
            list.plus(movieProperty)
        }
        Log.i("MovieResult", list[0].toString())
        return MovieResult(list)
    }
}

data class MovieApiResult(
    val result: List<MovieApiProperty>
)

data class MovieApiProperty(
    val id: String,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val movieImg: String
)