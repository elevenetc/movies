package com.elevenetc.apps.movies.core

data class MovieDetails(
    val movieId: String,
    val title: String,
    val overview: String,
    val genres: List<String>,
    val releaseDate: String,
    val imageUrl: String
)