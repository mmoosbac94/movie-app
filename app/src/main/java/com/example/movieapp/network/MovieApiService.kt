package com.example.movieapp.network

import android.util.Log
import com.example.movieapp.database.MovieProperty
import com.example.movieapp.database.MovieResult
import com.example.movieapp.database.MovieType
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.jetbrains.annotations.Nullable
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/"
private const val API_KEY = "0c97571ddf07813f8e4e1712ab264a77"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface MovieApiService {
    @GET("3/movie/popular?api_key=$API_KEY&language=en-US&page=1")
    suspend fun getPopularMovies(): MovieApiResult

    @GET("3/movie/top_rated?api_key=$API_KEY&language=en-US&page=1")
    suspend fun getTopRatedMovies(): MovieApiResult

    @GET("3/search/movie?api_key=$API_KEY&language=en-US&query=&page=1&include_adult=false")
    suspend fun getSpecificMovie(@Query("query") movie: String): MovieApiResult

}

object MovieApi {

    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }

    fun convertToDdModelWithType(
        movieApiResult: MovieApiResult,
        type: String = MovieType.default
    ): MovieResult {
        val list: MutableList<MovieProperty> = mutableListOf()

        movieApiResult.results.map {
            val movieProperty = MovieProperty(
                id = it.id,
                title = it.title,
                overview = it.overview,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
                movieImg = it.movieImg,
                type = type
            )
            list.add(movieProperty)
        }
        return MovieResult(list)
    }
}

data class MovieApiResult(
    val results: List<MovieApiProperty>
)

data class MovieApiProperty(
    var id: String = "",
    var title: String = "",
    var overview: String = "",
    @Json(name = "release_date") var releaseDate: String = "",
    @Json(name = "vote_average") var voteAverage: Double = 0.0,
    @Json(name = "poster_path") var movieImg: String = ""
)