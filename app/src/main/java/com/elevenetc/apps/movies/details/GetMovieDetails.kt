package com.elevenetc.apps.movies.details

import com.elevenetc.apps.movies.core.MovieDetails
import com.elevenetc.apps.movies.core.network.MoviesApi
import com.elevenetc.apps.movies.core.utils.UseCase
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDetails @Inject constructor(
    private val api: MoviesApi
) : UseCase() {
    fun get(movieId: String): Single<MovieDetails> {
        return api.getMovieDetails(movieId)
    }
}