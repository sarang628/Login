package com.sryang.torang.compose.email

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sryang.torang.uistate.EmailLoginUiState
import com.sryang.torang.viewmodels.EmailLoginViewModel

@Composable
internal fun EmailLoginScreen(
    viewModel: EmailLoginViewModel = hiltViewModel(),
    onLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLogin by viewModel.isLogin.collectAsState(false)
    EmailLoginScreen(
        uiState, isLogin, onLogin = { id, password -> viewModel.login(id, password, onLogin) },
        onChangeEmail = { viewModel.onChangeEmail(it) },
        onChangePassword = { viewModel.onChangePassword(it) },
        onClearEmail = { viewModel.clearEmail() },
        onClearPassword = { viewModel.clearPassword() },
        onLogout = { viewModel.logout({ }) },
        onClearErrorMsg = { viewModel.clearErrorMsg() }
    )
}

@Composable
internal fun EmailLoginScreen(
    uiState: EmailLoginUiState,
    isLogin: Boolean,
    onLogin: (id: String, password: String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onClearEmail: () -> Unit,
    onClearPassword: () -> Unit,
    onLogout: () -> Unit,
    onClearErrorMsg: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                AlertDialog(onDismissRequest = { onClearErrorMsg.invoke() },
                    confirmButton = {
                        Button(onClick = { onClearErrorMsg.invoke() }) {
                            Text(text = "확인")
                        }
                    },
                    title = {
                        Text(text = it, fontSize = 16.sp)
                    })
            }
        }
    }
}

@Preview
@Composable
fun PreviewEmailLoginScreen() {
    EmailLoginScreen(
        uiState = EmailLoginUiState(error = "로그인에 실패하였습니다."),
        isLogin = false,
        onLogin = { id, password -> },
        onClearPassword = {},
        onClearEmail = {},
        onChangePassword = {},
        onChangeEmail = {},
        onLogout = {},
        onClearErrorMsg = {}
    )
}