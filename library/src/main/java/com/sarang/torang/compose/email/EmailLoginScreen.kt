package com.sarang.torang.compose.email

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.sarang.torang.compose.TorangLogo
import com.sarang.torang.uistate.EmailLoginUiState
import com.sarang.torang.uistate.LoginUiState
import com.sarang.torang.viewmodels.EmailLoginViewModel
import kotlinx.coroutines.android.awaitFrame

@Composable
fun EmailLoginScreen(
    viewModel: EmailLoginViewModel = hiltViewModel(),
    onLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    EmailLoginScreen(
        uiState = uiState,
        onLogin = { id, password -> viewModel.login(id, password, onLogin) },
        onChangeEmail = { viewModel.onChangeEmail(it) },
        onChangePassword = { viewModel.onChangePassword(it) },
        onClearEmail = { viewModel.clearEmail() },
        onClearPassword = { viewModel.clearPassword() },
        onClearErrorMsg = { viewModel.clearErrorMsg() }
    )
}

@Composable
internal fun EmailLoginScreen(
    uiState: EmailLoginUiState,
    onLogin: (id: String, password: String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onClearEmail: () -> Unit,
    onClearPassword: () -> Unit,
    onClearErrorMsg: () -> Unit,
) {
    Box {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

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
        uiState = EmailLoginUiState(
            /*error = "로그인에 실패하였습니다.",*/
            email = "torang@torang.com",
            password = "password",
            emailErrorMessage = "emailErrorMessage",
            passwordErrorMessage = "passwordErrorMessage",
            isProgress = false
        ),
        onLogin = { id, password -> },
        onClearPassword = {},
        onClearEmail = {},
        onChangePassword = {},
        onChangeEmail = {},
        onClearErrorMsg = {}
    )
}