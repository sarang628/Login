package com.sarang.toringlogin

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.sarang.toringlogin.login.LoginActivity
import com.sryang.torang_core.navigation.LoginNavigation
import javax.inject.Inject

class LoginNavigationImpl @Inject constructor() : LoginNavigation {
    override fun goLogin(fragmentManager: FragmentManager?) {
        
    }

    override fun goLogin(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }
}