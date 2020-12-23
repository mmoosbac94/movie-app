package com.example.movieapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class MovieResult(
    val results: List<MovieProperty>
)

@Entity(tableName = "movie_property_table")
data class MovieProperty(
    @PrimaryKey
    var id: String,
    val title: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "poster_path") val movieImg: String,
    val type: String
)