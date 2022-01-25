package com.milad.moviesdb.data.repository

import com.milad.moviesdb.data.local.dao.GenresDao
import com.milad.moviesdb.data.local.dao.MoviesDao
import com.milad.moviesdb.data.local.dao.SummariesDao
import com.milad.moviesdb.data.remote.api.MovieService
import com.milad.moviesdb.model.entity.Genre
import com.milad.moviesdb.model.entity.Movie
import com.milad.moviesdb.model.entity.Summary
import com.milad.moviesdb.utils.isCacheExpired
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

interface MovieRepository {

  fun loadMovies(page: Int): Flow<Resource<List<Summary>>>

  fun loadGenres() :  Flow<List<Genre>>

  fun loadMovieDetails(movieId: Int): Flow<Resource<Movie>>
}

@ExperimentalCoroutinesApi
class DefaultMovieRepository @Inject constructor(
  private val movieService: MovieService,
  private val moviesDao: MoviesDao,
  private val summariesDao: SummariesDao,
  private val genresDao: GenresDao
) : MovieRepository {

  override fun loadMovies(page: Int): Flow<Resource<List<Summary>>> {
    return flow<Resource<List<Summary>>> {
      var movies = summariesDao.getMovieList(page)
      if (movies.isNullOrEmpty() || isCacheExpired(movies.first().updatedAt!!)) {
        val response = movieService.getMovies(page)
        if (response.isSuccessful) {
          summariesDao.deleteAllSummariesByPage(page)
          if (!response.body()!!.results.isNullOrEmpty()) {
            var now = Calendar.getInstance().time
            movies = response.body()!!.results
            movies.forEach {
              it.page = page
              it.updatedAt = now.time
            }
            summariesDao.addSummaries(movies)
          }
        } else {
          emit(Resource.Failed<List<Summary>>("Network error!"))
        }
      }
      emit(Resource.Success<List<Summary>>(movies))
    }.flowOn(Dispatchers.IO)
  }


  override fun loadGenres() :  Flow<List<Genre>> {
    return flow<List<Genre>> {
      var genres = genresDao.getAllGenres()
      if (genres.isNullOrEmpty() || isCacheExpired(genres.first().updatedAt!!)) {
        val response = movieService.getGenres()
        if (response.isSuccessful) {
          genresDao.deleteAllGenres()
          if (!response.body()!!.genres.isNullOrEmpty()) {
            var now = Calendar.getInstance().time
            genres = response.body()!!.genres
            genres.forEach {
              it.updatedAt = now.time
            }
            genresDao.addGenres(genres)
          }
        } else {
          emit(ArrayList())
        }
      }
      emit(genres)
    }.flowOn(Dispatchers.IO)
  }

  override fun loadMovieDetails(movieId: Int): Flow<Resource<Movie>> {
    return flow<Resource<Movie>> {
      var movie = moviesDao.getMovieById(movieId)
      if (movie == null || isCacheExpired(movie.updatedAt!!)) {
        val response = movieService.getMovieDetails(movieId)
        if (response.isSuccessful) {
          moviesDao.deleteMovieById(movieId)
          if (response.body() != null) {
            movie = response.body()!!
            movie.updatedAt = Calendar.getInstance().time.time
            moviesDao.addMovie(movie)
          }
        } else {
          emit(Resource.Failed<Movie>("Network error!"))
        }
      }
      emit(Resource.Success<Movie>(movie))
    }.flowOn(Dispatchers.IO)
  }
}
