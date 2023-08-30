package com.sarang.toringlogin.login.email

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun TestEmailLoginScreen() {
    _EmailLoginScreen(
        onLogin = {},
        onLogout = {},
        isLogin = true,
        isProgress = false,
        isLoginSuccess = false
    )
}

@Preview
@Composable
internal fun TestEmailLoginScreen1() {
    _EmailLoginScreen(
        onLogin = {},
        onLogout = {},
        isLogin = false,
        isProgress = false,
        isLoginSuccess = true
    )
}
