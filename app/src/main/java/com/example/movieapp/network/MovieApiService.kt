package com.example.movieapp.network

import com.example.movieapp.database.MovieProperty
import com.example.movieapp.database.MovieResult
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

//private val moshi = Moshi.Builder().add(NullToEmptyStringAdapter).add(KotlinJsonAdapterFactory()).build()
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

    @GET("3/search/movie?api_key=0c97571ddf07813f8e4e1712ab264a77&language=en-US&query=&page=1&include_adult=false")
    suspend fun getSpecificMovie(@Query("query") movie: String): MovieApiResult

}

object MovieApi {

    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }

    fun convertToDdModelWithType(
        movieApiResult: MovieApiResult,
        type: String = "default"
    ): MovieResult {
        val list: MutableList<MovieProperty> = mutableListOf()

        movieApiResult.results.map {
            val movieProperty: MovieProperty = MovieProperty(
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
    var _id: String?,
    var _title: String?,
    var _overview: String?,
    @Json(name = "release_date") var _releaseDate: String?,
    @Json(name = "vote_average") var _voteAverage: Double?,
    @Json(name = "poster_path") var _movieImg: String?
) {

    var id: String
        get() = _id ?: ""
        set(value) {
            _id = value
        }

    var title: String
        get() = _title ?: ""
        set(value) {
            _title = value
        }

    var overview: String
        get() = _overview ?: ""
        set(value) {
            _overview = value
        }

    var releaseDate: String
        get() = _releaseDate ?: ""
        set(value) {
            _releaseDate = value
        }

    var voteAverage: Double
        get() = _voteAverage ?: 0.0
        set(value) {
            _voteAverage = value
        }

    var movieImg: String
        get() = _movieImg ?: ""
        set(value) {
            _movieImg = value
        }
}