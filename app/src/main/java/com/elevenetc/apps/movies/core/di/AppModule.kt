package com.elevenetc.apps.movies.core.di

import android.content.Context
import com.elevenetc.apps.movies.core.bitmaps.BitmapsLoader
import com.elevenetc.apps.movies.core.bitmaps.BitmapsLoaderImpl
import com.elevenetc.apps.movies.core.cache.MoviesCache
import com.elevenetc.apps.movies.core.cache.MoviesCacheImpl
import com.elevenetc.apps.movies.core.cache.PopularMoviesCache
import com.elevenetc.apps.movies.core.cache.PopularMoviesCacheImpl
import com.elevenetc.apps.movies.core.logging.Logger
import com.elevenetc.apps.movies.core.logging.LoggerImpl
import com.elevenetc.apps.movies.core.navigation.MainActivityKeeper
import com.elevenetc.apps.movies.core.navigation.MainActivityKeeperImpl
import com.elevenetc.apps.movies.core.navigation.Navigator
import com.elevenetc.apps.movies.core.navigation.NavigatorImpl
import com.elevenetc.apps.movies.core.network.MoviesApi
import com.elevenetc.apps.movies.core.network.moviedb.TheMovieDbApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideMoviesApi(inst: TheMovieDbApi): MoviesApi = inst

    @Provides
    fun provideMoviesCache(inst: MoviesCacheImpl): MoviesCache = inst

    @Provides
    fun providePopularMoviesCache(inst: PopularMoviesCacheImpl): PopularMoviesCache = inst

    @Provides
    fun provideNavigator(inst: NavigatorImpl): Navigator = inst

    @Provides
    fun provideBitmapsLoader(inst: BitmapsLoaderImpl): BitmapsLoader = inst

    @Provides
    fun provideLogger(inst: LoggerImpl): Logger = inst

    @Singleton
    @Provides
    fun provideActivityKeeper(inst: MainActivityKeeperImpl): MainActivityKeeper = inst
}