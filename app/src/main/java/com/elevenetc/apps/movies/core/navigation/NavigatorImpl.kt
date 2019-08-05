package com.elevenetc.apps.movies.core.navigation

import android.os.Bundle
import android.support.v4.app.Fragment
import com.elevenetc.apps.movies.R
import com.elevenetc.apps.movies.core.BaseFragment
import com.elevenetc.apps.movies.core.utils.KeyboardUtils
import com.elevenetc.apps.movies.details.MovieDetailsFragment
import com.elevenetc.apps.movies.popular.PopularMoviesFragment
import com.elevenetc.apps.movies.trailers.YoutubeTrailerViewerFragment
import javax.inject.Inject

class NavigatorImpl @Inject constructor(
    private val activityKeeper: MainActivityKeeper
) : Navigator {

    override fun onAppStarted(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            activityKeeper.getActivity()!!.supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, PopularMoviesFragment())
                .commit()
        }
    }

    override fun openMovieDetails(movieId: String, current: BaseFragment) {
        KeyboardUtils.hideKeyboard(current.view!!)
        addToBackStack({ MovieDetailsFragment.newInstance(movieId) }, "details", current)
    }

    override fun openMovieTrailer(movieId: String, current: BaseFragment) {
        KeyboardUtils.hideKeyboard(current.view!!)
        addToBackStack({ YoutubeTrailerViewerFragment.newInstance(movieId) }, "trailer", current)
    }

    override fun goBack() {
        activityKeeper.getActivity()!!.supportFragmentManager.popBackStack()
    }

    override fun addToBackStack(
        newFragmentFactory: () -> Fragment,
        newFragmentTag: String,
        current: BaseFragment
    ) {

        val newFragment = activityKeeper.getActivity()!!.supportFragmentManager.findFragmentByTag(newFragmentTag)

        if (newFragment != null) {
            activityKeeper.getActivity()!!.supportFragmentManager
                .beginTransaction()
                .hide(current)
                .show(newFragment)
                .addToBackStack(null)
                .commit()
        } else {
            activityKeeper.getActivity()!!.supportFragmentManager
                .beginTransaction()
                .hide(current)
                .add(R.id.fragment_container, newFragmentFactory(), newFragmentTag)
                .addToBackStack(null)
                .commit()
        }
    }
}