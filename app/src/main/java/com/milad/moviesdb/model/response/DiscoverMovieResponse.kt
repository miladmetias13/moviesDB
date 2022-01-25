package com.milad.moviesdb.model.response

import com.milad.moviesdb.model.entity.Summary


data class DiscoverMovieResponse(
    val page: Int,
    val results: List<Summary>,
    val total_results: Int,
    val total_pages: Int
)
