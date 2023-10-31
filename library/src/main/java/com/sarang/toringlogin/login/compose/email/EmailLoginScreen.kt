package com.sarang.toringlogin.login.compose.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(colorResource(id = com.sarang.theme.R.color.colorSecondaryLight))
    ) {
        TorangLogo()
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (!isLogin) {
                EmailLoginInput(
                    onLogin = { id, password -> viewModel.login(id, password) },
                    progress = uiState.isProgress,
                    email = uiState.email,
                    password = uiState.password,
                    onChangeEmail = { viewModel.onChangeEmail(it) },
                    onChangePassword = { viewModel.onChangePassword(it) },
                    emailErrorMessage = uiState.emailErrorMessage,
                    passwordErrorMessage = uiState.passwordErrorMessage,
                    onClearEmail = {viewModel.clearEmail()},
                    onClearPassword = {viewModel.clearPassword()}
                )
            } else {
                LogedIn {
                    viewModel.logout({ })
                }
            }
            uiState.error?.let {
                Text(text = "로그인에 실패하였습니다.\n$it", color = Color.Red)
            }
        }
    }
}