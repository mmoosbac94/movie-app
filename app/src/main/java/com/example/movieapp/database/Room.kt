package com.example.movieapp.database

import android.content.Context
import androidx.room.*
import com.example.movieapp.models.MovieProperty

@Dao
interface MovieDao {

    @Query("select * from movie_property_table WHERE type LIKE :type Order by voteAverage DESC")
    fun getPopularMovies(type: String): List<MovieProperty>

    @Query("select * from movie_property_table WHERE type LIKE :type Order by voteAverage DESC")
    fun getTopRatedMovies(type: String): List<MovieProperty>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieProperties: List<MovieProperty>)

}


@Database(entities = [MovieProperty::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}


private lateinit var INSTANCE: MoviesDatabase

fun getDatabase(context: Context): MoviesDatabase {
    synchronized(MoviesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java,
                "movies_database"
            ).build()
        }
    }
    return INSTANCE
}