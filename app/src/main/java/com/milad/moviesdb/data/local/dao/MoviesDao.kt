package com.milad.moviesdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.milad.moviesdb.model.entity.Movie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: Movie)

    @Query("DELETE FROM ${Movie.TABLE_NAME}")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM ${Movie.TABLE_NAME} WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE id = :movieId")
    fun getMovieById(movieId: Int): Movie

    @Query("SELECT * FROM ${Movie.TABLE_NAME}")
    fun getAllMovies(): List<Movie>

}
