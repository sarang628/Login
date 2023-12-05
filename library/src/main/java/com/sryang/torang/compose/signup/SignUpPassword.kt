package com.sryang.torang.compose.signup

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sryang.torang.compose.email.LoginOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPassword(
    password: String,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
    onNext: () -> Unit
) {
    var visiblePassword by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
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
            Text(
                text = "Create a password",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Create a password with at least 6 letters on numbers. It should be something others can't guess")
            Spacer(modifier = Modifier.height(12.dp))
            LoginOutlinedTextField(
                label = "Password",
                value = password,
                onValueChange = onValueChange,
                placeHolder = "Password",
                onClear = { visiblePassword = !visiblePassword },
                errorMessage = errorMessage,
                isPassword = true,
                isPasswordVisual = visiblePassword
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
fun PreviewSignUpPassword() {
    SignUpPassword(
        password = "password",
        onClear = {},
        onValueChange = {},
        onBack = {},
        onNext = {}
    )
}