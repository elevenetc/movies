package com.elevenetc.apps.movies.core.navigation

import android.support.v7.app.AppCompatActivity
import com.elevenetc.apps.movies.core.MainActivity
import javax.inject.Inject

class MainActivityKeeperImpl @Inject constructor(

) : MainActivityKeeper {

    private var activity: MainActivity? = null

    override fun onActivityCreated(activity: MainActivity) {
        this.activity = activity
    }

    override fun onActivityDestroyed() {
        this.activity = null
    }

    override fun getActivity(): AppCompatActivity? {
        return activity as AppCompatActivity
    }


}