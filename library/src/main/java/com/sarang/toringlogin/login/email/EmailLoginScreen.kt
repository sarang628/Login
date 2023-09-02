package com.sarang.toringlogin.login.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.toringlogin.login.EmailLogin
import com.sryang.torang_repository.services.impl.getLoginService
import com.sryang.torang_repository.session.SessionService
import com.sryang.torang_repository.session.TestSessionService
import kotlinx.coroutines.launch

@Composable
internal fun InternalEmailLoginScreen(
    onLogin: (EmailLogin) -> Unit,
    onLogout: (Void?) -> Unit,
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
                    onLogout.invoke(null)
                }
            }
        }
    }
}

@Composable
fun EmailLoginScreen(
    onSuccessLogin: (String) -> Unit,
) {
    val coroutine = rememberCoroutineScope()
    val loginService = getLoginService(context = LocalContext.current)
    var progress by remember { mutableStateOf(false) }
    var isFailedLogin by remember { mutableStateOf(false) }
    val sessionService = SessionService(LocalContext.current)
    val isLogin by sessionService.isLogin.collectAsState()

    Column {
        InternalEmailLoginScreen(
            onLogin = {
                coroutine.launch {
                    progress = true
                    try {
                        val response = loginService.emailLogin(it.email, it.password)
                        sessionService.saveToken(response.token)
                        sessionService.saveToken("a")
                        onSuccessLogin.invoke(response.token)
                        isFailedLogin = false
                    } catch (e: Exception) {
                        isFailedLogin = true
                    }
                    progress = false
                }
            },
            isLogin = isLogin,
            isProgress = progress,
            onLogout = {
                coroutine.launch {
                    sessionService.removeToken()
                }
            },
            isFailedLogin = isFailedLogin
        )
    }
}

@Preview
@Composable
fun PreviewEmailLoginScreen() {
    EmailLoginScreen(onSuccessLogin = {

    })
}


@Preview
@Composable
fun Test123() {
    TestSessionService()
}