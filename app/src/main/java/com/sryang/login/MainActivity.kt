package com.sryang.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sarang.toringlogin.login.LoginLogicImpl
import com.sryang.library.LoginLogic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            val loginLogic : LoginLogic = LoginLogicImpl()
            loginLogic.LoginScreen()
        }

    }
}