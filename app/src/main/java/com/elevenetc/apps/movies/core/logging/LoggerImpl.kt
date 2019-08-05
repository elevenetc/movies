package com.elevenetc.apps.movies.core.logging

import javax.inject.Inject

class LoggerImpl @Inject constructor() : Logger {
    override fun log(throwable: Throwable) {
        throwable.printStackTrace()
    }
}