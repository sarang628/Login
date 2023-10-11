package com.sarang.toringlogin.login.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.sarang.toringlogin.login.EmailLogin
import kotlinx.coroutines.launch

@Composable
internal fun InternalEmailLoginScreen(
    onLogin: (EmailLogin) -> Unit,
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
                EmailLoginForm(onLogin = {
                    onLogin.invoke(it)
                }, progress = isProgress, isFailedLogin = error != null)
            } else {
                LogedIn {
                    onLogout.invoke()
                }
            }
            error?.let {
                Text(text = it)
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
    onLogout: () -> Unit,
    progress: Boolean,
    error: String?
) {
    Column {
        InternalEmailLoginScreen(
            onLogin = onLogin,
            isLogin = isLogin,
            isProgress = progress,
            onLogout = onLogout,
            error = error
        )
    }
}