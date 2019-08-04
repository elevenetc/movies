package com.elevenetc.apps.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.elevenetc.apps.movies.core.BaseFragment
import com.elevenetc.apps.movies.core.Keys
import com.elevenetc.apps.movies.core.net.TheMovieDbApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieId = arguments!!.getString(Keys.MOVIE_ID)
        TheMovieDbApi().getMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ details ->
                view.findViewById<TextView>(R.id.text_title).setText(details.title)
                view.findViewById<View>(R.id.btn_trailer).setOnClickListener { openTrailer(details.movieId) }

            }, {
                it.printStackTrace()
            })
    }

    private fun openTrailer(movieId: String) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MovieTrailerFragment.newInstance(movieId))
            .addToBackStack("trailer")
            .commit()
    }

    companion object {

        fun newInstance(movieId: String): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(Keys.MOVIE_ID, movieId)
                }
            }
        }
    }
}