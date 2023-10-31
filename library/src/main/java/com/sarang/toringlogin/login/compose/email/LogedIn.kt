package com.sarang.toringlogin.login.compose.email

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun LogedIn(onLogout: () -> Unit) {
    Button(
        onClick = { onLogout.invoke() },
        Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "Log out")
    }
}

@Preview
@Composable
fun PreviewLogedIn() {
    LogedIn(onLogout = {

    })
}