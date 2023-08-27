package com.sarang.toringlogin.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.sryang.torang_repository.services.impl.LoginService
import com.sryang.torang_repository.services.impl.getLoginService
import com.sryang.torang_repository.session.SessionService
import kotlinx.coroutines.launch

@Composable
fun EmailLoginScreen(
    onLogin: (EmailLogin) -> Unit,
    loginService: LoginService,
    onSuccessLogin: (Void?) -> Unit
) {
    val coroutine = rememberCoroutineScope()
    var progress by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }
    val context = LocalContext.current
    val sessionService = SessionService(LocalContext.current)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = com.sarang.theme.R.color.colorSecondaryLight))
    ) {
        TorangLogo()
        if (sessionService.getToken() == null) {
            EmailLoginForm(onLogin = {
                coroutine.launch {
                    progress = true
                    try {
                        val response = loginService.emailLogin(it.email, it.password)
                        result = response.token
                        sessionService.saveToken(response.token)
                        onSuccessLogin.invoke(null)
                    } catch (e: Exception) {
                        result = e.toString()
                    }
                    progress = false
                }
            }, progress = progress)
            Text(text = result)
        } else {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "로그인 되었습니다.")
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "로그이웃")
                }
            }
        }
    }
}

@Composable
fun TorangLogo() {
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
}

@Composable
fun EmailLoginForm(onLogin: (EmailLogin) -> Unit, progress: Boolean) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
        }) {
            Text(text = "Login")
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
        }, loginService = loginService, onSuccessLogin = {}
    )
}