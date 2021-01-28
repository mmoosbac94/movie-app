package com.example.movieapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

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


class MovieType {
    companion object {
        const val default = "default"
        const val popular = "popular"
        const val topRated = "topRated"
    }
}
