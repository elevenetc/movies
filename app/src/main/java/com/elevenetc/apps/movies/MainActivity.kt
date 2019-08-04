package com.elevenetc.apps.movies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, PopularMoviesFragment())
                .commit()

//            val fragment = YouTubePlayerSupportFragment()
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container, fragment)
//                //.add(R.id.fragment_container, MovieTrailerFragment())
//                .commit()
//
//            fragment.initialize("AIzaSyDbIxIgpnEhAAq5pU45dV07TMExtIavvks", object : YouTubePlayer.OnInitializedListener {
//                override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, player: YouTubePlayer, p2: Boolean) {
//                    player.cueVideo("hA6hldpSTF8")
//                }
//
//                override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
//                    if (p1 != null) {
//
//                    }
//                }
//            })
        }
    }
}
