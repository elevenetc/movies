package com.elevenetc.apps.movies.core.navigation

import android.os.Bundle
import android.support.v4.app.Fragment
import com.elevenetc.apps.movies.core.BaseFragment

/**
 * Encapsulated screens navigation logic and screens implementations/instances
 */
interface Navigator {
    fun onAppStarted(savedInstanceState: Bundle?)
    fun openMovieDetails(movieId: String, current: BaseFragment)
    fun openMovieTrailer(movieId: String, current: BaseFragment)
    fun goBack()
    fun addToBackStack(
        newFragmentFactory: () -> Fragment,
        newFragmentTag: String,
        current: BaseFragment
    )
}