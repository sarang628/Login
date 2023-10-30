package com.sarang.toringlogin.login.compose.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

@Composable
internal fun EmailLoginScreen(
    onLogin: (id: String, password: String) -> Unit,
    onLogout: () -> Unit,
    isLogin: Boolean,
    isProgress: Boolean,
    error: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = com.sarang.theme.R.color.colorSecondaryLight))
    ) {
        TorangLogo()
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!isLogin) {
                EmailLoginInput(onLogin = onLogin, progress = isProgress)
            } else {
                LogedIn {
                    onLogout.invoke()
                }
            }
            error?.let {
                Text(text = "로그인에 실패하였습니다.\n$it", color = Color.Red)
            }
        }
    }
}