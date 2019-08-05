package com.elevenetc.apps.movies.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.elevenetc.apps.movies.R
import com.elevenetc.apps.movies.core.Movie
import com.elevenetc.apps.movies.core.bitmaps.BitmapsLoader
import com.elevenetc.apps.movies.core.bitmaps.BitmapsLoaderImpl

class GridViewAdapter(
    movies: List<Movie>,
    private val itemSize: Int,
    private val bitmapsLoader: BitmapsLoader,
    private val clickListener: (movieId: String) -> Unit
) : BaseAdapter() {

    private val movies = movies.toMutableList()

    fun clear() {
        movies.clear()
        notifyDataSetChanged()
    }

    fun append(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val imageView: ImageView
        val titleTextView: TextView
        val itemView: View

        if (convertView == null) {

            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
            itemView.layoutParams = ViewGroup.LayoutParams(itemSize, itemSize)

            imageView = itemView.findViewById(R.id.image_view)
            titleTextView = itemView.findViewById(R.id.text_title)
        } else {
            itemView = convertView as ViewGroup
            imageView = itemView.findViewById(R.id.image_view)
            titleTextView = itemView.findViewById(R.id.text_title)
        }

        val movie = movies[position]

        titleTextView.text = movie.title
        bitmapsLoader.load(movie.previewPictureUrl, imageView)

        itemView.setOnClickListener { clickListener(movie.id) }

        return itemView
    }

    override fun getItem(position: Int): Any {
        return movies[position]
    }

    override fun getItemId(position: Int): Long {
        return 1
    }

    override fun getCount(): Int {
        return movies.size
    }

    fun getMovies(): List<Movie> {
        return movies
    }


}