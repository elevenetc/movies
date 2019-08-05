package com.elevenetc.apps.movies.core.utils

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics

object ViewUtils {
    fun getScreenWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }
}