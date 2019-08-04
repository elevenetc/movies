package com.elevenetc.apps.movies.core.net

import com.elevenetc.apps.movies.core.Movie
import com.elevenetc.apps.movies.core.MovieDetails
import com.elevenetc.apps.movies.core.MovieTrailer
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class TheMovieDbApi {

    val apiKey = ""

    val api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(App::class.java)

    fun getMovieTrailer(movieId: String): Single<MovieTrailer> {
        return api.getMovieTrailer(movieId, apiKey).map {
            MovieTrailer(it.results.first().key)
        }
    }

    fun getMovieDetails(movieId: String): Single<MovieDetails> {
        return api.getMovieDetails(movieId, apiKey).map {
            MovieDetails(movieId, it.title, it.overview)
        }
    }

    fun getPopular(page: Int): Single<List<Movie>> {
        return api.getPopular(page, apiKey).map { response ->
            response.results.map { m -> Movie(m.id, m.title, "https://image.tmdb.org/t/p/w200" + m.poster_path) }
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
    }
}