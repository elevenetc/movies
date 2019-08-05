package com.elevenetc.apps.movies.trailers

import com.elevenetc.apps.movies.core.MovieTrailer
import com.elevenetc.apps.movies.core.network.MoviesApi
import com.elevenetc.apps.movies.core.utils.UseCase
import io.reactivex.Single
import javax.inject.Inject

class GetTrailer @Inject constructor(val api: MoviesApi) : UseCase() {
    fun get(movieId: String): Single<MovieTrailer> {
        return api.getMovieTrailer(movieId)
    }
}