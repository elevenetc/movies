package com.elevenetc.apps.movies.trailers

import android.os.Bundle
import android.view.View
import com.elevenetc.apps.movies.BuildConfig
import com.elevenetc.apps.movies.core.App
import com.elevenetc.apps.movies.core.BaseActivity
import com.elevenetc.apps.movies.core.Keys
import com.elevenetc.apps.movies.core.Keys.YOUTUBE_PAUSE_TIME
import com.elevenetc.apps.movies.core.Keys.YOUTUBE_VIEW_ID
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class YoutubeTrailerViewerFragment : YouTubePlayerSupportFragment(), YouTubePlayer.OnInitializedListener {

    private var youtubeVideoId = ""
    private var pauseTime = 0

    private lateinit var player: YouTubePlayer
    private val components by lazy { (context!!.applicationContext as App).appComponent }
    private val subs = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            youtubeVideoId = savedInstanceState.getString(YOUTUBE_VIEW_ID, "")
            pauseTime = savedInstanceState.getInt(YOUTUBE_PAUSE_TIME, 0)
        }

        initialize(BuildConfig.API_KEY_GOOGLE, this)
    }

    override fun onInitializationSuccess(
        privider: YouTubePlayer.Provider?,
        player: YouTubePlayer,
        wasRestored: Boolean
    ) {
        this.player = player
        player.setPlayerStateChangeListener(YoutubeStateListener { closeFragment() })

        if (wasRestored) {
            if (youtubeVideoId.isEmpty()) loadTrailer()
            else playVideo(pauseTime)
        } else {
            loadTrailer()
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        result: YouTubeInitializationResult?
    ) {
        components.logger().log(RuntimeException(result.toString()))
    }

    override fun onSaveInstanceState(state: Bundle) {
        state.putString(YOUTUBE_VIEW_ID, youtubeVideoId)
        state.putInt(YOUTUBE_PAUSE_TIME, player.currentTimeMillis)
        super.onSaveInstanceState(state)
    }

    override fun onDestroyView() {
        subs.clear()
        super.onDestroyView()
    }

    private fun closeFragment() {
        (activity as BaseActivity).components.navigator().goBack()
    }

    private fun loadTrailer() {

        val movieId = arguments!!.getString(Keys.MOVIE_ID)!!

        subs.add(
            components.trailers().viewModel().getTrailer(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    youtubeVideoId = it.youtubeVideoId
                    playVideo()
                }, {
                    components.logger().log(it)
                })
        )
    }

    private fun playVideo(from: Int = 0) {
        player.loadVideo(youtubeVideoId, from)
    }


    companion object {

        fun newInstance(movieId: String): YoutubeTrailerViewerFragment {
            return YoutubeTrailerViewerFragment().apply {
                arguments = Bundle().apply {
                    putString(Keys.MOVIE_ID, movieId)
                }
            }
        }
    }
}