package com.elevenetc.apps.movies.core.network.moviedb

data class MovieDetailsDto(
    val overview: String,
    val title: String,
    val poster_path: String,
    val original_language: String,
    val release_date: String,
    val genres: List<GenresDto.GenreDto>
)