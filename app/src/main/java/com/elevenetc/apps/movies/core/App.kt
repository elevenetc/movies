package com.elevenetc.apps.movies.core

import android.app.Application
import com.elevenetc.apps.movies.core.di.AppComponent
import com.elevenetc.apps.movies.core.di.AppModule
import com.elevenetc.apps.movies.core.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}