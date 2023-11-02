package com.sarang.toringlogin.login.compose.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.toringlogin.login.compose.email.LoginOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpEmail(
    email: String,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
    onNext: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(

                ),
                title = {
                    Text("")
                },
                navigationIcon = {
                    IconButton(onClick = { onBack.invoke() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(text = "What's your email?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Enter the email where you can be contacted. No one will see this on your profile")
            Spacer(modifier = Modifier.height(12.dp))
            LoginOutlinedTextField(
                label = "Email",
                value = email,
                onValueChange = onValueChange,
                placeHolder = "Email",
                onClear = onClear,
                errorMessage = errorMessage
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(modifier = Modifier.fillMaxWidth(), onClick = { onNext.invoke() }) {
                Text(text = "Next")
            }
        }
    }
}


@Preview
@Composable
fun PreviewSignUp() {
    SignUpEmail(
        email = "",
        onClear = {},
        onValueChange = {},
        onBack = {},
        onNext = {}
    )
}