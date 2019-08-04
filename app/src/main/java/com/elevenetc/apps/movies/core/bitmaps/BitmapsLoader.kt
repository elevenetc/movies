package com.elevenetc.apps.movies.core.bitmaps

import android.widget.ImageView
import com.squareup.picasso.Picasso

class BitmapsLoader {
    fun load(url: String, view: ImageView) {
        Picasso.get()
            .load(url)
            .into(view)
    }
}