package com.milad.moviesdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.milad.moviesdb.model.entity.Summary
import kotlinx.coroutines.flow.Flow

@Dao
interface SummariesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSummaries(summaries: List<Summary>)

    @Query("DELETE FROM ${Summary.TABLE_NAME}")
    suspend fun deleteAllSummaries()

    @Query("DELETE FROM ${Summary.TABLE_NAME} WHERE page = :page")
    suspend fun deleteAllSummariesByPage(page: Int)

    @Query("SELECT * FROM ${Summary.TABLE_NAME} WHERE id = :summaryId")
    fun getSummaryById(summaryId: Int): Flow<Summary>

    @Query("SELECT * FROM ${Summary.TABLE_NAME}")
    fun getAllSummaries(): Flow<List<Summary>>

    @Query("SELECT COUNT(id) FROM ${Summary.TABLE_NAME}")
    fun getAllSummariesCount(): Flow<Int>

    @Query("SELECT * FROM ${Summary.TABLE_NAME} WHERE page = :page")
    fun getMovieList(page: Int): List<Summary>
}
