package com.elevenetc.apps.movies.core

open class MovieTrailer(
    val youtubeVideoId: String
)

object NoMovieTrailer : MovieTrailer("")