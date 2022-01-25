

package com.milad.moviesdb.di.module

import com.milad.moviesdb.data.repository.DefaultMovieRepository
import com.milad.moviesdb.data.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class MoviesRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindMoviesRepository(repository: DefaultMovieRepository): MovieRepository
}
