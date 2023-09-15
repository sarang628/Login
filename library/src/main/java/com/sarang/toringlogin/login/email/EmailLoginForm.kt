package com.sarang.toringlogin.login.email

import android.util.Log
import android.view.KeyEvent.ACTION_DOWN
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.toringlogin.login.EmailLogin

@Preview
@Composable
fun PreviewEmailLoginForm() {
    Column {
        EmailLoginForm(onLogin = {}, progress = true, isFailedLogin = false)
        EmailLoginForm(onLogin = {}, progress = true, isFailedLogin = true)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmailLoginForm(
    onLogin: (EmailLogin) -> Unit,
    progress: Boolean,
    isFailedLogin: Boolean
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Box(Modifier.height(250.dp)) {
        if (progress)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier
                    .onPreviewKeyEvent {
                        Log.d("__sryang", it.key.toString())
                        if (it.key == Key.Tab && it.nativeKeyEvent.action == ACTION_DOWN
                            || it.key == Key.NavigateNext && it.nativeKeyEvent.action == ACTION_DOWN
                        ) {
                            focusManager.moveFocus(FocusDirection.Down)
                            true
                        } else {
                            false
                        }
                    }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier
                    .onPreviewKeyEvent {
                        if (
                            it.key == Key.Tab && it.nativeKeyEvent.action == ACTION_DOWN
                            || it.key == Key.NavigateNext && it.nativeKeyEvent.action == ACTION_DOWN
                        ) {
                            focusManager.moveFocus(FocusDirection.Down)
                            true
                        } else {
                            false
                        }
                    }

            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                onLogin.invoke(
                    EmailLogin(
                        email = email,
                        password = password
                    )
                )
            }) {
                Text(text = "Login")
            }
            if (isFailedLogin)
                Text(text = "로그인에 실패하였습니다.")
        }
    }
}