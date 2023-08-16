package com.sarang.toringlogin.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sryang.library.LoginLogic
import com.sryang.library.LoginResult
import com.sryang.library.LogoutResult

class LoginLogicImpl : LoginLogic {

    @Preview
    @Composable
    override fun LoginScreen() {
        LoginScreen()
    }

    override suspend fun requestLogin(email: String, password: String, loginResult: LoginResult) {
        TODO("Not yet implemented")
    }

    override suspend fun requestLogout(logoutResult: LogoutResult) {
        TODO("Not yet implemented")
    }

}