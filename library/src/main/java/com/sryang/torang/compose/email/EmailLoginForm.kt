package com.sryang.torang.compose.email

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun EmailLoginInput(
    onLogin: (id: String, password: String) -> Unit,    // 로그인 클릭
    progress: Boolean,                                  // 로그인 프로그레스
    emailErrorMessage: String? = null,                  // 이메일 입력 에러 메세지
    passwordErrorMessage: String? = null,               // 비밀번호 입력 에러 메세지
    email: String,                                      // 이메일
    password: String,                                   // 비밀번호
    onChangeEmail: (String) -> Unit,                    // 이메일 입력
    onChangePassword: (String) -> Unit,                 // 비밀번호 임력
    onClearEmail: () -> Unit,                           // 이메일 초기화
    onClearPassword: () -> Unit                         // 비밀번호 초기화
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Box(Modifier.height(250.dp)) {
        ConstraintLayout {
            Spacer(modifier = Modifier.height(100.dp))
            Column(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginOutlinedTextField(
                    value = email,
                    onValueChange = onChangeEmail,
                    label = "Email",
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onKeyTabOrDown = { focusManager.moveFocus(FocusDirection.Down) },
                    placeHolder = "이메일을 입력해주세요.",
                    errorMessage = emailErrorMessage,
                    onClear = { onClearEmail.invoke() }
                )
                Spacer(modifier = Modifier.height(10.dp))
                LoginOutlinedTextField(
                    value = password,
                    onValueChange = onChangePassword,
                    label = "Password",
                    onNext = { focusManager.clearFocus(true) },
                    onKeyTabOrDown = { focusManager.clearFocus(true) },
                    placeHolder = "비밀번호를 입력해주세요.",
                    errorMessage = passwordErrorMessage,
                    onClear = { isPasswordVisible = !isPasswordVisible },
                    isPassword = true,
                    isPasswordVisual = isPasswordVisible
                )
                Spacer(modifier = Modifier.height(15.dp))
                LoginButton(
                    onClick = { onLogin.invoke(email, password) }, progress = progress
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewEmailLoginInput() {
    EmailLoginInput(
        onLogin = { id, password -> },
        progress = false,
        email = "",
        password = "",
        onChangeEmail = {},
        onChangePassword = {},
        onClearEmail = { /*TODO*/ }) {

    }
}