package com.elevenetc.apps.movies.popular

import com.elevenetc.apps.movies.core.Movie
import com.elevenetc.apps.movies.core.cache.MoviesCache
import com.elevenetc.apps.movies.core.cache.PopularMoviesCache
import com.elevenetc.apps.movies.core.network.MoviesApi
import com.elevenetc.apps.movies.core.utils.UseCase
import io.reactivex.Single
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val api: MoviesApi,
    private val moviesCache: MoviesCache,
    private val popularMoviesCache: PopularMoviesCache
) : UseCase() {
    fun get(page: Int): Single<List<Movie>> {
        return api.getPopular(page).map { result ->
            cacheMovies(result, page)
            result
        }.onErrorResumeNext {
            getCached(page)
        }
    }

    private fun getCached(page: Int): Single<List<Movie>> {
        return Single.just(popularMoviesCache.get(page).map {
            moviesCache.get(it)!!
        })
    }

    private fun cacheMovies(result: List<Movie>, page: Int) {
        moviesCache.put(result)
        popularMoviesCache.put(page, result.map { it.id })
    }
}