package com.elevenetc.apps.movies.details

import com.elevenetc.apps.movies.core.MovieDetails
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val checkTrailer: CheckIfTrailerAvailable,
    private val getDetails: GetMovieDetails
) {

    fun getDetails(movieId: String): Single<MovieDetails> {
        return getDetails.get(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun checkIfTrailerAvailable(movieId: String): Single<Boolean> {
        return checkTrailer.check(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}