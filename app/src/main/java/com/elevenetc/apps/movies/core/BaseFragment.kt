package com.elevenetc.apps.movies.core

import android.support.v4.app.Fragment
import butterknife.Unbinder
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {
    val subs = CompositeDisposable()
    val components by lazy { (context!!.applicationContext as App).appComponent }
    var unbinder: Unbinder? = null

    override fun onDestroyView() {
        unbinder?.unbind()
        super.onDestroyView()
    }
}