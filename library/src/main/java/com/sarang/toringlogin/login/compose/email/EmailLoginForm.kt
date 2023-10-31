package com.sarang.toringlogin.login.compose.email

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun EmailLoginInput(
    onLogin: (id: String, password: String) -> Unit,
    progress: Boolean,
    emailErrorMessage: String? = null,
    passwordErrorMessage: String? = null,
    email: String,
    password: String,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onClearEmail: () -> Unit,
    onClearPassword: () -> Unit
) {
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
                    onClear = { onClearPassword.invoke() }

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