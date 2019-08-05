package com.elevenetc.apps.movies.trailers

import com.elevenetc.apps.movies.core.MovieTrailer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TrailerViewModel @Inject constructor(
    private val getTrailer: GetTrailer
) {
    fun getTrailer(movieId: String): Single<MovieTrailer> {
        return getTrailer.get(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}