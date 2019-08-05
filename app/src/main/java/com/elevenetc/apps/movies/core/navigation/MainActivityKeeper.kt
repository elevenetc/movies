package com.elevenetc.apps.movies.core.navigation

import android.support.v7.app.AppCompatActivity
import com.elevenetc.apps.movies.core.MainActivity

interface MainActivityKeeper {
    fun getActivity(): AppCompatActivity?
    fun onActivityDestroyed()
    fun onActivityCreated(activity: MainActivity)
}