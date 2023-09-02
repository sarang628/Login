package com.sarang.toringlogin.login.email

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun TestEmailLoginScreen() {
    InternalEmailLoginScreen(
        onLogin = {},
        onLogout = {},
        isLogin = true,
        isProgress = false,
        isFailedLogin = false
    )
}

@Preview
@Composable
internal fun TestEmailLoginScreen1() {
    InternalEmailLoginScreen(
        onLogin = {},
        onLogout = {},
        isLogin = false,
        isProgress = false,
        isFailedLogin = true
    )
}
