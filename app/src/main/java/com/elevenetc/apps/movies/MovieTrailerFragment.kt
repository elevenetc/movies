package com.elevenetc.apps.movies

import android.os.Bundle
import android.view.View
import com.elevenetc.apps.movies.core.Keys
import com.elevenetc.apps.movies.core.net.TheMovieDbApi
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieTrailerFragment : YouTubePlayerSupportFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        initialize("", object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                privider: YouTubePlayer.Provider?,
                player: YouTubePlayer,
                wasRestored: Boolean
            ) {

                if (!wasRestored) {

                    val movieId = arguments!!.getString(Keys.MOVIE_ID)

                    TheMovieDbApi().getMovieTrailer(movieId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            player.loadVideo(it.youtubeVideoId)
                        }, {

                        })

                    //player.cueVideo("hA6hldpSTF8")

                }


                player.setPlayerStateChangeListener(object : YouTubePlayer.PlayerStateChangeListener {
                    override fun onAdStarted() {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onLoading() {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onVideoStarted() {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onLoaded(p0: String?) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onVideoEnded() {
                        activity!!.finish()
                    }

                    override fun onError(p0: YouTubePlayer.ErrorReason?) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })



                player.setPlaybackEventListener(object : YouTubePlayer.PlaybackEventListener {
                    override fun onSeekTo(p0: Int) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onBuffering(p0: Boolean) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onPlaying() {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onStopped() {


                        if (player.currentTimeMillis == player.durationMillis) {
                            //activity!!.finish()
                        }

                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onPaused() {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                if (p1 != null) {

                }
            }
        })


    }

    companion object {

        fun newInstance(movieId: String): MovieTrailerFragment {
            return MovieTrailerFragment().apply {
                arguments = Bundle().apply {
                    putString(Keys.MOVIE_ID, movieId)
                }
            }
        }
    }
}