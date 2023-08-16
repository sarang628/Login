package com.sarang.toringlogin.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EmailLoginScreen() {
    Column {
        Text(text = "Email")
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Email") }
        )
        Text(text = "Password")
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Password") }
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Login")
        }
    }
}