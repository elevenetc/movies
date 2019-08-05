package com.elevenetc.apps.movies.core.cache

import android.content.Context
import com.google.gson.Gson
import javax.inject.Inject

class PopularMoviesCacheImpl @Inject constructor(
    private val context: Context
) : PopularMoviesCache {

    private val keyValue = context.getSharedPreferences("popular-movies", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun put(page: Int, ids: List<String>) {
        keyValue.edit().putStringSet(page.toString(), ids.toSet()).apply()
    }

    override fun get(page: Int): List<String> {
        return if (keyValue.contains(page.toString())) {
            keyValue.getStringSet(page.toString(), emptySet())!!.toList()
        } else {
            emptyList()
        }
    }
}