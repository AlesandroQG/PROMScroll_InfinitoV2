package com.alesandro.scroll_infinito

import android.app.Application

/**
 * Primera clase por la que pase la aplicación cuando se inicia
 */
class TaskApplication:Application() {
    companion object {
        lateinit var prefs: Preferences
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Preferences(baseContext)
    }

}