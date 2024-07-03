package com.sarang.torang.compose.email

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sarang.torang.R

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
fun SignInForm(
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
            LoginOutlinedTextField(
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
            LoginOutlinedTextField(
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
            LoginButton(
                onClick = onLogin::invoke, progress = progress
            )
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewSignInForm() {
    SignInForm(/*Preview*/
        onLogin = { },
        progress = false,
        email = "",
        password = "",
        onChangeEmail = {},
        onChangePassword = {},
        onClearEmail = { /*TODO*/ }
    )
}