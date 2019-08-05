package com.elevenetc.apps.movies.core

import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    val components by lazy { (applicationContext as App).appComponent }
}