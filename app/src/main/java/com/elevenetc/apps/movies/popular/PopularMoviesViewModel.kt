package com.elevenetc.apps.movies.popular

import com.elevenetc.apps.movies.core.Movie
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopularMoviesViewModel @Inject constructor(
    private val getMovies: GetPopularMovies,
    private val searchMovies: SearchMovies
) {
    fun getPopularMovies(page: Int): Single<List<Movie>> {
        return getMovies.get(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchMovies(query: String, page: Int): Single<List<Movie>> {
        return searchMovies.search(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}