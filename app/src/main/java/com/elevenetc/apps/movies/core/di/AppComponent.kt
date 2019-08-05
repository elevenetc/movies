package com.elevenetc.apps.movies.core.di

import com.elevenetc.apps.movies.core.bitmaps.BitmapsLoader
import com.elevenetc.apps.movies.core.logging.Logger
import com.elevenetc.apps.movies.core.navigation.MainActivityKeeper
import com.elevenetc.apps.movies.core.navigation.Navigator
import com.elevenetc.apps.movies.details.DetailsComponent
import com.elevenetc.apps.movies.popular.PopularMoviesComponent
import com.elevenetc.apps.movies.trailers.TrailerViewerComponent
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun navigator(): Navigator
    fun activityKeeper(): MainActivityKeeper
    fun bitmaps(): BitmapsLoader
    fun logger(): Logger

    fun popularMovies(): PopularMoviesComponent
    fun details(): DetailsComponent
    fun trailers(): TrailerViewerComponent
}