package com.elevenetc.apps.movies.trailers

import dagger.Subcomponent

@Subcomponent
interface TrailerViewerComponent {
    fun viewModel(): TrailerViewModel
}