package com.elevenetc.apps.movies.popular

import dagger.Subcomponent

@Subcomponent
interface PopularMoviesComponent {
    fun viewModel(): PopularMoviesViewModel
}