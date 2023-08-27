package com.sarang.toringlogin.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.toringlogin.R
import com.sryang.torang_repository.services.LoginService
import com.sryang.torang_repository.services.impl.getLoginService
import kotlinx.coroutines.launch

@Composable
fun EmailLoginScreen(
    onLogin: (EmailLogin) -> Unit,
    loginService: LoginService
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutine = rememberCoroutineScope()
    var progress by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = com.sarang.theme.R.color.colorSecondaryLight))
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "T O R A N G",
                color = colorResource(id = com.sarang.theme.R.color.colorSecondary),
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hit the spot",
                color = colorResource(id = com.sarang.theme.R.color.colorSecondary),
                fontSize = 20.sp
            )
        }
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
                .height(300.dp)
                .fillMaxWidth(),
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
                        result = loginService.emailLogin(email, password).toString()

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
        }, loginService = loginService
    )
}