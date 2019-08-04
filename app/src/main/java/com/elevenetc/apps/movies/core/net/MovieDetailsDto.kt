package com.elevenetc.apps.movies.core.net

data class MovieDetailsDto(
    val overview: String,
    val title: String,
    val poster_path: String,
    val original_language: String
)