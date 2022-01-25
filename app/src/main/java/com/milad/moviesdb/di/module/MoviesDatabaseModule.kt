package com.milad.moviesdb.di.module

import android.app.Application
import com.milad.moviesdb.data.local.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MoviesDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = MoviesDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideSummariesDao(database: MoviesDatabase) = database.getSummariesDao()

    @Singleton
    @Provides
    fun provideGenresDao(database: MoviesDatabase) = database.getGenreDao()

    @Singleton
    @Provides
    fun provideMoviesDao(database: MoviesDatabase) = database.getMoviesDao()
}
