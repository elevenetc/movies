package com.elevenetc.apps.movies.core.net

data class MoviesDto(
    val page:Int,
    val results:List<MovieDto>
)