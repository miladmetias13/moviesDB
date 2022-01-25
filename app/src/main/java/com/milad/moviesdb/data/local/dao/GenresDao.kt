package com.milad.moviesdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.milad.moviesdb.model.entity.Genre

@Dao
interface GenresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenres(genres: List<Genre>)

    @Query("DELETE FROM ${Genre.TABLE_NAME}")
    suspend fun deleteAllGenres()

    @Query("SELECT * FROM ${Genre.TABLE_NAME} WHERE ID = :genreId")
    fun getGenreById(genreId: Int): Genre

    @Query("SELECT * FROM ${Genre.TABLE_NAME}")
    fun getAllGenres(): List<Genre>
}
