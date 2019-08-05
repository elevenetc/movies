package com.elevenetc.apps.movies.details

import com.elevenetc.apps.movies.core.network.MoviesApi
import com.elevenetc.apps.movies.core.utils.UseCase
import io.reactivex.Single
import javax.inject.Inject

class CheckIfTrailerAvailable @Inject constructor(
    private val api: MoviesApi
) : UseCase() {
    fun check(movieId: String): Single<Boolean> {
        return api.checkIfTrailerAvailable(movieId)
    }
}