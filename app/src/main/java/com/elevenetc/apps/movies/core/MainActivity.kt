package com.elevenetc.apps.movies.core

import android.os.Bundle
import com.elevenetc.apps.movies.R


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        components.activityKeeper().onActivityCreated(this)
        components.navigator().onAppStarted(savedInstanceState)
    }

    override fun onDestroy() {
        components.activityKeeper().onActivityDestroyed()
        super.onDestroy()
    }
}
