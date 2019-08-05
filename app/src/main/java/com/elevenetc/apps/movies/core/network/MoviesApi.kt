package com.elevenetc.apps.movies.core.network

import com.elevenetc.apps.movies.core.Movie
import com.elevenetc.apps.movies.core.MovieDetails
import com.elevenetc.apps.movies.core.MovieTrailer
import io.reactivex.Single

interface MoviesApi {
    fun getMovieTrailer(movieId: String): Single<MovieTrailer>
    fun getMovieDetails(movieId: String): Single<MovieDetails>
    fun getPopular(page: Int): Single<List<Movie>>
    fun searchMovies(query: String, page: Int): Single<List<Movie>>
    fun checkIfTrailerAvailable(movieId: String): Single<Boolean>
}