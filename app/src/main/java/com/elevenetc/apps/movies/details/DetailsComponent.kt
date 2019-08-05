package com.elevenetc.apps.movies.details

import dagger.Subcomponent

@Subcomponent
interface DetailsComponent {
    fun viewModel(): DetailsViewModel
}