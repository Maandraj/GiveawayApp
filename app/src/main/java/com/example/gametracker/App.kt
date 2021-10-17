package com.example.gametracker

import android.app.Application
import com.chibatching.kotpref.Kotpref
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(baseContext)
    }
}