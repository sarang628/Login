package com.sarang.toringlogin.login.compose.email

import android.util.Log
import android.view.KeyEvent.ACTION_DOWN
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Preview
@Composable
fun PreviewEmailLoginForm() {
    Column {
        EmailLoginInput(onLogin = { id, passrod -> {} }, progress = false)
        EmailLoginInput(onLogin = { id, passrod -> {} }, progress = true)
    }
}

@Composable
fun EmailLoginInput(
    onLogin: (id: String, password: String) -> Unit,
    progress: Boolean,
    emailErrorMessage: String? = null,
    passwordErrorMessage: String? = null
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
                    onValueChange = { email = it },
                    label = "Email",
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    onDown = { focusManager.moveFocus(FocusDirection.Down) },
                    placeHolder = "이메일을 입력해주세요.",
                    errorMessage = emailErrorMessage
                )
                LoginOutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.clearFocus(true)
                    }),
                    onDown = { focusManager.moveFocus(FocusDirection.Down) },
                    placeHolder = "비밀번호를 입력해주세요.",
                    errorMessage = passwordErrorMessage

                )
                Spacer(modifier = Modifier.height(10.dp))
                LoginButton(
                    onClick = { onLogin.invoke(email, password) }, progress = progress
                )
            }
        }
    }
}

@Composable
fun LoginOutlinedTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    placeHolder: String,
    onDown: () -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions { }
) {

    val compose = @Composable {
        Text(text = errorMessage ?: "", color = Color.Red)
    }

    Column {
        androidx.compose.material3.OutlinedTextField(
            label = { Text(text = label) },
            value = value,
            onValueChange = onValueChange,
            isError = !errorMessage.isNullOrEmpty(), //빨강 라인
            supportingText = if (errorMessage != null) compose else null,
            trailingIcon = { // 오류 아이콘
                errorMessage?.let {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_menu_help),
                        contentDescription = ""
                    )
                }
            },
            placeholder = { Text(text = placeHolder) }, // 힌트
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = keyboardActions,
            modifier = Modifier
                .onPreviewKeyEvent {
                    if (it.key == Key.Tab && it.nativeKeyEvent.action == ACTION_DOWN
                        || it.key == Key.NavigateNext && it.nativeKeyEvent.action == ACTION_DOWN
                    ) {
                        Log.d("LoginOutlinedTextField", "onDown")
                        onDown.invoke()
                        true
                    } else {
                        false
                    }
                }
        )
    }
}

@Preview
@Composable
fun PreviewLoginOutlinedTextField() {
    LoginOutlinedTextField(
        label = "",
        onDown = {},
        onValueChange = {},
        placeHolder = "",
        value = ""
    )
}

@Composable
fun LoginButton(onClick: () -> Unit, progress: Boolean, modifier: Modifier = Modifier) {
    Button(onClick = onClick, enabled = !progress, modifier = modifier.width(100.dp)) {
        if (progress)
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(25.dp),
                strokeWidth = 3.dp
            )
        else
            Text(text = "Login")
    }
}


@Preview
@Composable
fun PreviewLoginButton() {
    LoginButton(onClick = { /*TODO*/ }, progress = false)
}
