package com.example.movieapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class MovieResult(
    val results: List<MovieProperty>
)

// Need of Parcelize here because we pass movieProperty as an argument from one fragment to another.
@Parcelize
@Entity(tableName = "movie_property_table")
data class MovieProperty(
    @PrimaryKey
    var id: String,
    val title: String,
    val overview: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "poster_path") val movieImg: String,
    val type: String

): Parcelable
