package com.sarang.toringlogin.login

import androidx.compose.runtime.Composable
import com.sryang.library.LoginLogic
import com.sryang.library.LoginResult
import com.sryang.library.LogoutResult

class LoginLogicImpl : LoginLogic {

    @Composable
    override fun LoginScreen() {
    }

    override suspend fun requestLogin(email: String, password: String, loginResult: LoginResult) {
        TODO("Not yet implemented")
    }

    override suspend fun requestLogout(logoutResult: LogoutResult) {
        TODO("Not yet implemented")
    }

}