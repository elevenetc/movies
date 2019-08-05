package com.elevenetc.apps.movies.core.bitmaps

import android.widget.ImageView
import com.squareup.picasso.Picasso
import javax.inject.Inject

class BitmapsLoaderImpl @Inject constructor() : BitmapsLoader {
    override fun load(url: String, view: ImageView) {
        Picasso.get()
            .load(url)
            .into(view)
    }
}