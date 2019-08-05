package com.elevenetc.apps.movies.core.cache

import android.content.Context
import com.elevenetc.apps.movies.core.Movie
import com.google.gson.Gson
import javax.inject.Inject

class MoviesCacheImpl @Inject constructor(
    private val context: Context
) : MoviesCache {

    private val keyValue = context.getSharedPreferences("movies", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun put(movies: List<Movie>) {
        movies.forEach { m ->
            put(m)
        }
    }

    override fun put(movie: Movie) {
        keyValue.edit().putString(movie.id, gson.toJson(movie)).apply()
    }

    override fun get(movieId: String): Movie? {
        return if (keyValue.contains(movieId)) {
            gson.fromJson(keyValue.getString(movieId, ""), Movie::class.java)
        } else {
            null
        }
    }
}