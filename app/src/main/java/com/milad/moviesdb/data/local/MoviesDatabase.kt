package com.milad.moviesdb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.milad.moviesdb.data.local.dao.GenresDao
import com.milad.moviesdb.data.local.dao.MoviesDao
import com.milad.moviesdb.data.local.dao.SummariesDao
import com.milad.moviesdb.model.entity.Genre
import com.milad.moviesdb.model.entity.Movie
import com.milad.moviesdb.model.entity.Summary

@Database(
    entities = [Movie::class, Summary::class, Genre::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(IntArrayTypeConverter::class, DateTypeConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao
    abstract fun getGenreDao(): GenresDao
    abstract fun getSummariesDao(): SummariesDao

    companion object {
        const val DB_NAME = "movies_database"

        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
