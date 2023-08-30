package com.sarang.toringlogin.login.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.toringlogin.login.EmailLogin
import com.sryang.torang_repository.services.impl.getLoginService
import com.sryang.torang_repository.session.SessionService
import kotlinx.coroutines.launch

@Composable
internal fun _EmailLoginScreen(
    onLogin: (EmailLogin) -> Unit,
    onLogout: (Void?) -> Unit,
    isLogin: Boolean,
    isProgress: Boolean,
    isLoginSuccess: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = com.sarang.theme.R.color.colorSecondaryLight))
    ) {
        TorangLogo()
        if (isLogin) {
            EmailLoginForm(onLogin = {
                onLogin.invoke(it)
            }, progress = isProgress, isSuccessLogin = isLoginSuccess)
        } else {
            LogedIn({})
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
    val sessionService = SessionService(LocalContext.current)
    var isSuccessLogin by remember { mutableStateOf(false) }
    _EmailLoginScreen(
        onLogin = {
            coroutine.launch {
                progress = true
                try {
                    val response = loginService.emailLogin(it.email, it.password)
                    sessionService.saveToken(response.token)
                    onSuccessLogin.invoke(response.token)
                } catch (e: Exception) {
                    isSuccessLogin = true
                }
                progress = false
            }
        },
        isLogin = (sessionService.getToken() != null && sessionService.getToken().equals("")),
        isProgress = progress,
        onLogout = {
            sessionService.removeToken()
        },
        isLoginSuccess = isSuccessLogin
    )
}

@Preview
@Composable
fun PreviewEmailLoginScreen() {
    EmailLoginScreen(onSuccessLogin = {

    })
}