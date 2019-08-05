package com.elevenetc.apps.movies.core.network.moviedb

data class GenresDto(val genres: List<GenreDto>) {
    data class GenreDto(val id: String, val name: String)
}