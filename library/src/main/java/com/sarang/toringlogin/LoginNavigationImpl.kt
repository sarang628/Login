package com.sarang.toringlogin

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.example.torang_core.navigation.LoginNavigation
import com.sarang.toringlogin.login.LoginActivity
import javax.inject.Inject

class LoginNavigationImpl @Inject constructor() : LoginNavigation {
    override fun goLogin(fragmentManager: FragmentManager?) {
        
    }

    override fun goLogin(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }
}