package com.example.movieapp.database

import android.content.Context
import androidx.room.*

@Dao
interface MovieDao {

    @Query("select * from movie_property_table ORDER BY voteAverage DESC")
    fun getMovies(): List<MovieProperty>

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