package com.elevenetc.apps.movies.core.cache

import com.elevenetc.apps.movies.core.Movie

interface MoviesCache {
    fun put(movies: List<Movie>)
    fun put(movie: Movie)
    fun get(movieId: String): Movie?
}