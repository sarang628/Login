package com.sarang.toringlogin.login.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.toringlogin.login.EmailLogin
import kotlinx.coroutines.launch

@Composable
internal fun InternalEmailLoginScreen(
    onLogin: (EmailLogin) -> Unit,
    onLogout: () -> Unit,
    isLogin: Boolean,
    isProgress: Boolean,
    isFailedLogin: Boolean
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
                EmailLoginForm(onLogin = {
                    onLogin.invoke(it)
                }, progress = isProgress, isFailedLogin = isFailedLogin)
            } else {
                LogedIn {
                    onLogout.invoke()
                }
            }
        }
    }
}

data class EmailLogin(
    val id: String,
    val password: String
)

@Composable
fun EmailLoginScreen(
    isLogin: Boolean,
    onLogin: (EmailLogin) -> Unit,
    onLogout: () -> Unit
) {
    val coroutine = rememberCoroutineScope()
    var progress by remember { mutableStateOf(false) }
    var isFailedLogin by remember { mutableStateOf(false) }

    Column {
        InternalEmailLoginScreen(
            onLogin = {
                coroutine.launch {
                    progress = true
                    try {
                        onLogin.invoke(EmailLogin(it.email, it.password))
                        isFailedLogin = false
                    } catch (e: Exception) {
                        isFailedLogin = true
                    }
                    progress = false
                }
            },
            isLogin = isLogin,
            isProgress = progress,
            onLogout = onLogout,
            isFailedLogin = isFailedLogin
        )
    }
}

@Preview
@Composable
fun PreviewEmailLoginScreen() {
    EmailLoginScreen(
        isLogin = false, onLogin = {}, onLogout = {}
    )
}