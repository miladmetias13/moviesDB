package com.milad.moviesdb.model.response

import com.milad.moviesdb.model.entity.Genre


data class DiscoverGenreResponse(
    var genres: List<Genre>,
)
