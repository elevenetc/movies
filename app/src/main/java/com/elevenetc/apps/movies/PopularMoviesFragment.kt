package com.elevenetc.apps.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.elevenetc.apps.movies.core.BaseFragment
import com.elevenetc.apps.movies.core.Movie
import com.elevenetc.apps.movies.core.bitmaps.BitmapsLoader
import com.elevenetc.apps.movies.core.net.TheMovieDbApi
import com.elevenetc.apps.movies.core.utils.ChangedTextListener
import com.elevenetc.apps.movies.core.utils.EndScrollListener
import com.elevenetc.apps.movies.core.utils.GlobalLayoutListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PopularMoviesFragment : BaseFragment() {

    private val columns = 3

    private var currentPage = 1
    private lateinit var adapter: GridViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val gridView = view.findViewById<GridView>(R.id.grid_view)
        val btnRetry = view.findViewById<View>(R.id.btn_retry)
        val editSearch = view.findViewById<EditText>(R.id.edit_search)

        gridView.numColumns = columns
        gridView.setOnScrollListener(EndScrollListener { onEndScroll() })



        editSearch.addTextChangedListener(ChangedTextListener { onEditSearchChanged(it) })

        btnRetry.setOnClickListener { loadNextPage() }


        GlobalLayoutListener(gridView) {

            val gridWidth = gridView.width

            adapter = GridViewAdapter(emptyList(), gridWidth / columns, BitmapsLoader()) { openDetails(it) }
            gridView.adapter = adapter

            loadPage(currentPage)
        }
    }

    private fun openDetails(movieId: String) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, MovieDetailsFragment.newInstance(movieId))
            .addToBackStack( "details")
            .commit()
    }

    private fun loadPage(page: Int) {
        TheMovieDbApi().getPopular(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                adapter.append(movies)
            }, {
                it.printStackTrace()
            })
    }

    private fun onEditSearchChanged(query: String) {
        //loadFirstPage(query)
    }

    private fun onEndScroll() {
        loadNextPage()
    }

    private fun loadNextPage() {
        loadPage(++currentPage)
    }

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

                itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
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


    }
}