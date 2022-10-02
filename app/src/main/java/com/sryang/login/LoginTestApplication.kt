package com.sryang.login

import android.app.Application
import com.sryang.torang_core.login.FacebookLoginProvider
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