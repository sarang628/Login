package com.sarang.toringlogin.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.sryang.torang_repository.services.LoginService
import com.sryang.torang_repository.services.impl.getLoginService
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailLoginScreen(
    onLogin: (EmailLogin) -> Unit,
    //loginService: LoginService
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutine = rememberCoroutineScope()
    var progress by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }

    Box() {
        if (progress)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                maxLines = 1
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                maxLines = 1
            )
            Button(onClick = {
                onLogin.invoke(
                    EmailLogin(
                        email = email,
                        password = password
                    )
                )
                coroutine.launch {
                    progress = true
                    try {
                        //result = loginService.emailLogin(email, password).toString()

                    } catch (e: Exception) {
                        result = e.toString()
                    }
                    progress = false
                }
            }) {
                Text(text = "Login")
            }
            if (result.equals("false")) {
                Text(text = "로그인에 실패하였습니다.")
            } else if (result.equals("true")) {
                Text(text = "로그인에 성공하였습니다.")
            }

        }
    }
}

data class EmailLogin(
    val email: String,
    val password: String
)

@Preview
@Composable
fun PreviewEmailLoginScreen() {
    val loginService = getLoginService(context = LocalContext.current)
    EmailLoginScreen(
        onLogin = {
            Log.d("__sryang", it.toString())
        }
        //, loginService = loginService
    )
}