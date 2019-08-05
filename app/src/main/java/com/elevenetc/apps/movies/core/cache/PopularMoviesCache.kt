package com.elevenetc.apps.movies.core.cache

interface PopularMoviesCache {
    fun put(page: Int, ids: List<String>)
    fun get(page: Int): List<String>
}