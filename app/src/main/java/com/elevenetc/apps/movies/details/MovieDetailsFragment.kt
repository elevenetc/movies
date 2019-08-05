package com.elevenetc.apps.movies.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.elevenetc.apps.movies.R
import com.elevenetc.apps.movies.core.BaseFragment
import com.elevenetc.apps.movies.core.Keys
import com.elevenetc.apps.movies.core.MovieDetails

class MovieDetailsFragment : BaseFragment() {

    init {
        retainInstance = true
    }

    @BindView(R.id.btn_trailer)
    lateinit var btnSeeTrailer: Button

    @BindView(R.id.content_container)
    lateinit var contentContainer: View

    @BindView(R.id.progress_view)
    lateinit var progressView: View

    @BindView(R.id.text_title)
    lateinit var textTitle: TextView

    @BindView(R.id.text_date)
    lateinit var textDate: TextView

    @BindView(R.id.text_genres)
    lateinit var textGenres: TextView

    @BindView(R.id.text_desciption)
    lateinit var textDesciption: TextView

    @BindView(R.id.image_view)
    lateinit var imageView: ImageView

    private val viewModel by lazy { components.details().viewModel() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        unbinder = ButterKnife.bind(this, view)

        val movieId = arguments!!.getString(Keys.MOVIE_ID)!!

        contentContainer.visibility = View.GONE
        progressView.visibility = View.VISIBLE
        btnSeeTrailer.visibility = View.GONE

        getDetails(movieId)
        checkIfTrailerAvailable(movieId)
    }

    private fun checkIfTrailerAvailable(movieId: String) {
        subs.add(
            viewModel.checkIfTrailerAvailable(movieId)
                .subscribe({ available ->
                    setTrailerAvailability(available, movieId)
                }, {
                    components.logger().log(it)
                    setTrailerAvailability(false, movieId)
                })
        )
    }

    private fun getDetails(movieId: String) {
        subs.add(
            viewModel.getDetails(movieId)
                .subscribe({ details ->
                    showDetails(details)
                }, {
                    showError(it)
                })
        )
    }

    private fun showError(it: Throwable) {
        components.logger().log(it)
        progressView.visibility = View.GONE
        Snackbar.make(view!!, R.string.generic_error, Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok) {
            components.navigator().goBack()
        }.show()
    }

    private fun showDetails(
        details: MovieDetails
    ) {
        contentContainer.visibility = View.VISIBLE
        progressView.visibility = View.GONE

        textTitle.text = details.title
        textDate.text = getString(R.string.details_release_date, details.releaseDate)
        textGenres.text = getString(R.string.genres, details.genres.joinToString { g -> g })
        textDesciption.text = details.overview

        components.bitmaps().load(details.imageUrl, imageView)
    }

    private fun setTrailerAvailability(
        available: Boolean,
        movieId: String
    ) {

        btnSeeTrailer.visibility = View.VISIBLE

        if (available) {
            btnSeeTrailer.isEnabled = true
            btnSeeTrailer.setText(R.string.trailer_see_trailer)
            btnSeeTrailer.setOnClickListener { openTrailer(movieId) }
        } else {
            btnSeeTrailer.isEnabled = false
            btnSeeTrailer.setText(R.string.trailer_no_available_trailer)
        }
    }

    private fun openTrailer(movieId: String) {
        components.navigator().openMovieTrailer(movieId, this)
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