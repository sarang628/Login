package com.sarang.toringlogin.login.compose.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.toringlogin.login.uistate.EmailLoginUiState
import com.sarang.toringlogin.login.viewmodels.EmailLoginViewModel

@Composable
internal fun EmailLoginScreen(
    viewModel: EmailLoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLogin by viewModel.isLogin.collectAsState()
    _EmailLoginScreen(
        uiState, isLogin, onLogin = { id, password -> viewModel.login(id, password) },
        onChangeEmail = { viewModel.onChangeEmail(it) },
        onChangePassword = { viewModel.onChangePassword(it) },
        onClearEmail = { viewModel.clearEmail() },
        onClearPassword = { viewModel.clearPassword() },
        onLogout = { viewModel.logout({ }) }
    )
}

@Composable
internal fun _EmailLoginScreen(
    uiState: EmailLoginUiState,
    isLogin: Boolean,
    onLogin: (id: String, password: String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onClearEmail: () -> Unit,
    onClearPassword: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = com.sarang.theme.R.color.colorSecondaryLight))
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        TorangLogo()
        Spacer(modifier = Modifier.height(60.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (!isLogin) {
                EmailLoginInput(
                    onLogin = onLogin,
                    onChangeEmail = onChangeEmail,
                    onChangePassword = onChangePassword,
                    onClearEmail = onClearEmail,
                    onClearPassword = onClearPassword,
                    progress = uiState.isProgress,
                    email = uiState.email,
                    password = uiState.password,
                    emailErrorMessage = uiState.emailErrorMessage,
                    passwordErrorMessage = uiState.passwordErrorMessage
                )
            } else {
                Column {
                    LogedIn(onLogout = onLogout)
                }
            }
            uiState.error?.let {
                Text(text = "로그인에 실패하였습니다.\n$it", color = Color.Red)
            }
        }
    }
}

@Preview
@Composable
fun PreviewEmailLoginScreen() {
    _EmailLoginScreen(
        uiState = EmailLoginUiState(),
        isLogin = true,
        onLogin = { id, password -> },
        onClearPassword = {},
        onClearEmail = {},
        onChangePassword = {},
        onChangeEmail = {},
        onLogout = {}
    )
}