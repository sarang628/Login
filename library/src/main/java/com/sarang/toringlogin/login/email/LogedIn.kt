package com.sarang.toringlogin.login.email

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun LogedIn(onLogout: (Void?) -> Unit) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "로그인 되었습니다.")
        Button(onClick = { onLogout.invoke(null) }) {
            Text(text = "로그이웃")
        }
    }
}

@Preview
@Composable
fun PreviewLogedIn() {
    LogedIn(onLogout = {

    })
}