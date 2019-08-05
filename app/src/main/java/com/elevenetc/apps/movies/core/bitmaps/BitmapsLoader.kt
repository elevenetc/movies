package com.elevenetc.apps.movies.core.bitmaps

import android.widget.ImageView

interface BitmapsLoader {
    fun load(url: String, view: ImageView)
}