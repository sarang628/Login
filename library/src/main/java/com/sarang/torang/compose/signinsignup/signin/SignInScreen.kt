package com.sarang.torang.compose.signinsignup.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.common.SignInTextField
import com.sarang.torang.data.LoginErrorMessage

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onLogin: () -> Unit,
) {
    val uiState = viewModel.uiState
    SignInScreen(
        uiState = uiState,
        onLogin = { viewModel.login(onLogin) },
        onChangeEmail = { viewModel.onChangeEmail(it) },
        onChangePassword = { viewModel.onChangePassword(it) },
        onClearEmail = { viewModel.clearEmail() },
        onClearErrorMsg = { viewModel.clearErrorMsg() }
    )
}

@Composable
internal fun SignInScreen(
    uiState: SignInUiState,
    onLogin: () -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onClearEmail: () -> Unit,
    onClearErrorMsg: () -> Unit,
) {
    Box {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SignInForm(
                onLogin = onLogin,
                onChangeEmail = onChangeEmail,
                onChangePassword = onChangePassword,
                onClearEmail = onClearEmail,
                progress = uiState.isProgress,
                email = uiState.email,
                password = uiState.password,
                emailErrorMessage = if (uiState.emailErrorMessage != null) stringResource(id = uiState.emailErrorMessage.resId) else null,
                passwordErrorMessage = if (uiState.passwordErrorMessage != null) stringResource(id = uiState.passwordErrorMessage.resId) else null
            )
            uiState.error?.let {
                AlertDialog(onDismissRequest = { onClearErrorMsg.invoke() },
                    confirmButton = {
                        Button(onClick = { onClearErrorMsg.invoke() }) {
                            Text(text = stringResource(id = R.string.confirm))
                        }
                    },
                    title = {
                        Text(text = it, fontSize = 16.sp)
                    })
            }
        }
    }
}

/**
 * 로그인 폼
 * @param onLogin 로그인 클릭
 * @param progress 로그인 프로그레스
 * @param emailErrorMessage 이메일 입력 에러 메세지
 * @param passwordErrorMessage 비밀번호 입력 에러 메세지
 * @param email 이메일
 * @param password 비밀번호
 * @param onChangeEmail 이메일 입력
 * @param onChangePassword 비밀번호 입력
 * @param onClearEmail 이메일 초기화
 */
@Composable
private fun SignInForm(
    onLogin: () -> Unit,
    progress: Boolean,
    emailErrorMessage: String? = null,
    passwordErrorMessage: String? = null,
    email: String,
    password: String,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onClearEmail: () -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInTextField(
                modifier = Modifier.testTag("EmailTextField"),
                value = email,
                onValueChange = onChangeEmail,
                label = stringResource(id = R.string.label_email),
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onKeyTabOrDown = { focusManager.moveFocus(FocusDirection.Down) },
                placeHolder = stringResource(id = R.string.email_place_holder),
                errorMessage = emailErrorMessage,
                onClear = { onClearEmail.invoke() },
                enable = !progress
            )
            Spacer(modifier = Modifier.height(10.dp))
            SignInTextField(
                modifier = Modifier.testTag("PasswordTextField"),
                value = password,
                onValueChange = onChangePassword,
                label = stringResource(id = R.string.label_password),
                onNext = { focusManager.clearFocus(true) },
                onKeyTabOrDown = { focusManager.clearFocus(true) },
                placeHolder = stringResource(id = R.string.password_place_holder),
                errorMessage = passwordErrorMessage,
                onClear = { isPasswordVisible = !isPasswordVisible },
                isPassword = true,
                isPasswordVisual = isPasswordVisible,
                enable = !progress
            )
            Spacer(modifier = Modifier.height(15.dp))
            SignInButton(
                onClick = onLogin::invoke, progress = progress
            )
        }
    }
}

@Preview
@Composable
fun PreviewSignInScreen() {
    SignInScreen(
        //uiState = EmailLoginUiState(),
        uiState = SignInUiState(
            //error = "로그인에 실패하였습니다.",
            email = "torang@torang.com",
            password = "password",
            emailErrorMessage = LoginErrorMessage.InvalidEmail,
            passwordErrorMessage = LoginErrorMessage.InvalidPassword,
            isProgress = false
        ),
        onLogin = { },
        onClearEmail = {},
        onChangePassword = {},
        onChangeEmail = {},
        onClearErrorMsg = {}
    )
}

@Composable
private fun SignInButton(onClick: () -> Unit, progress: Boolean, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        enabled = !progress,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("LoginBtn"),
        shape = RoundedCornerShape(15.dp)
    ) {
        if (progress)
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(25.dp),
                strokeWidth = 3.dp
            )
        else
            Text(text = stringResource(id = R.string.label_login))
    }
}

@Preview
@Composable
private fun PreviewLoginOutlinedTextField1() {
    SignInTextField(
        label = "label",
        onKeyTabOrDown = {},
        onValueChange = {},
        placeHolder = "placeHolder",
        value = "value",
        onNext = {},
        onClear = {},
        enable = false,
        errorMessage = "errorMessage"
    )
}

@Preview
@Composable
private fun PreviewSignInButton() {
    SignInButton(onClick = { /*TODO*/ }, progress = false)
}