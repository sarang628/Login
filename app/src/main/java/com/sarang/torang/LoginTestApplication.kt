package com.sarang.torang

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LoginTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}