package com.elevenetc.apps.movies.trailers

import com.google.android.youtube.player.YouTubePlayer

class YoutubeStateListener(val onVideoEndedHandler:() -> Unit): YouTubePlayer.PlayerStateChangeListener {
    override fun onAdStarted() {

    }

    override fun onLoading() {

    }

    override fun onVideoStarted() {

    }

    override fun onLoaded(p0: String?) {

    }

    override fun onVideoEnded() {
        onVideoEndedHandler()
    }

    override fun onError(p0: YouTubePlayer.ErrorReason?) {

    }
}