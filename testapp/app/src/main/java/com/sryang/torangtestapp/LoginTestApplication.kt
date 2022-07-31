package com.sryang.torangtestapp

import android.app.Application
import com.example.torang_core.login.FacebookLoginProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class LoginTestApplication : Application() {
    @Inject
    lateinit var facebookLoginProvider: FacebookLoginProvider

    override fun onCreate() {
        super.onCreate()
        facebookLoginProvider.onCreate()
    }
}