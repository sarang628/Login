package com.sryang.login

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LoginTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}