package com.milad.moviesdb.data.remote.api

import com.milad.moviesdb.model.entity.Movie
import com.milad.moviesdb.model.response.DiscoverGenreResponse
import com.milad.moviesdb.model.response.DiscoverMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun getMovies(@Query("page") page: Int ,@Query("api_key") API_Ket: String = API_Key): Response<DiscoverMovieResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(@Query("language") lang: String = "en-US" ,@Query("api_key") API_Ket: String = API_Key): Response<DiscoverGenreResponse>


    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int ,@Query("api_key") API_Ket: String = API_Key): Response<Movie>


    companion object {
        const val API_URL = "https://api.themoviedb.org/3/"
        const val API_Key = "c50f5aa4e7c95a2a553d29b81aad6dd0"
    }
}