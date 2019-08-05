package com.elevenetc.apps.movies.core.network.moviedb

import com.elevenetc.apps.movies.BuildConfig
import com.elevenetc.apps.movies.core.Movie
import com.elevenetc.apps.movies.core.MovieDetails
import com.elevenetc.apps.movies.core.MovieTrailer
import com.elevenetc.apps.movies.core.NoMovieTrailer
import com.elevenetc.apps.movies.core.network.MoviesApi
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class TheMovieDbApi @Inject constructor() : MoviesApi {

    private val apiKey = BuildConfig.API_KEY_MOVIES_DB
    private val baseUrl = "https://api.themoviedb.org/3/"
    private val smallPictureUrl = "https://image.tmdb.org/t/p/w200"
    private val largePictureUrl = "https://image.tmdb.org/t/p/w500"

    private val api = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(App::class.java)

    override fun getMovieTrailer(movieId: String): Single<MovieTrailer> {
        return api.getMovieTrailer(movieId, apiKey).map { response ->

            /**
             * Simplified version of looking for proper youtube trailer
             */
            val trailer = response.results.firstOrNull { it.site == "YouTube" }

            if (trailer == null) {
                NoMovieTrailer
            } else {
                MovieTrailer(trailer.key)
            }
        }
    }


    override fun getMovieDetails(movieId: String): Single<MovieDetails> {

        return api.getMovieDetails(movieId, apiKey).map {
            MovieDetails(
                movieId,
                it.title,
                it.overview,
                it.genres.map { g -> g.name },
                it.release_date,
                largePictureUrl + it.poster_path
            )
        }
    }

    override fun getPopular(page: Int): Single<List<Movie>> {
        return api.getPopular(page + 1, apiKey).map { response ->
            response.results.map { m -> Movie(m.id, m.title, smallPictureUrl + m.poster_path) }
        }
    }

    override fun searchMovies(query: String, page: Int): Single<List<Movie>> {
        return api.searchMovies(query, page + 1, apiKey).map { response ->
            response.results.map { m -> Movie(m.id, m.title, smallPictureUrl + m.poster_path) }
        }
    }

    override fun checkIfTrailerAvailable(movieId: String): Single<Boolean> {

        /**
         * TODO: replace with search call or specific call which check existence
         */

        return getMovieTrailer(movieId).map {
            it !is NoMovieTrailer
        }
    }

    interface App {
        @GET("movie/popular")
        fun getPopular(
            @Query("page") page: Int,
            @Query("api_key") apiKey: String
        ): Single<MoviesDto>

        @GET("movie/{id}")
        fun getMovieDetails(
            @Path("id") movieId: String,
            @Query("api_key") apiKey: String
        ): Single<MovieDetailsDto>

        @GET("movie/{id}/videos")
        fun getMovieTrailer(
            @Path("id") movieId: String,
            @Query("api_key") apiKey: String
        ): Single<MovieTrailersDto>

        @GET("search/movie")
        fun searchMovies(
            @Query("query") query: String,
            @Query("page") page: Int,
            @Query("api_key") apiKey: String
        ): Single<MoviesDto>
    }
}