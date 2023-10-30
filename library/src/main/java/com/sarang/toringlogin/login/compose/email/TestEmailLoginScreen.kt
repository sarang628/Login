package com.sarang.toringlogin.login.compose.email

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun TestEmailLoginScreen() {
    EmailLoginScreen(
        onLogin = { id, password ->  },
        onLogout = {},
        isLogin = true,
        isProgress = false
    )
}

@Preview
@Composable
internal fun TestEmailLoginScreen1() {
    EmailLoginScreen(
        onLogin = {id, password ->  },
        onLogout = {},
        isLogin = false,
        isProgress = false
    )
}
