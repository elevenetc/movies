package com.elevenetc.apps.movies.popular

import com.elevenetc.apps.movies.core.Movie
import com.elevenetc.apps.movies.core.network.MoviesApi
import com.elevenetc.apps.movies.core.utils.UseCase
import io.reactivex.Single
import javax.inject.Inject

class SearchMovies @Inject constructor(
    private val api: MoviesApi
) : UseCase() {
    fun search(query: String, page: Int): Single<List<Movie>> {
        return api.searchMovies(query, page)
    }
}