package com.sarang.toringlogin.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.sryang.library.LoginLogic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailLoginScreen(
    onLogin: (EmailLogin) -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        Text(text = "Email")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            maxLines = 1
        )
        Text(text = "Password")
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
    EmailLoginScreen(onLogin = {
        Log.d("__sryang", it.toString())
    })
}