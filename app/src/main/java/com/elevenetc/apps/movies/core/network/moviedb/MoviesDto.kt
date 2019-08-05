package com.elevenetc.apps.movies.core.network.moviedb

data class MoviesDto(
    val page:Int,
    val results:List<MovieDto>
)