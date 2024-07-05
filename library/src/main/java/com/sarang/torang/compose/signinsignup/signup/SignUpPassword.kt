package com.sarang.torang.compose.signinsignup.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signin.SignInTextField

@Composable
internal fun SignUpPassword(
    password: String,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
    onNext: () -> Unit
) {
    var visiblePassword by remember { mutableStateOf(false) }
    Column(
        Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create_a_password),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = stringResource(id = R.string.describe_input_password))
        Spacer(modifier = Modifier.height(12.dp))
        SignInTextField(
            label = stringResource(id = R.string.label_password),
            value = password,
            onValueChange = onValueChange,
            placeHolder = stringResource(id = R.string.password_place_holder),
            onClear = { visiblePassword = !visiblePassword },
            errorMessage = errorMessage,
            isPassword = true,
            isPasswordVisual = visiblePassword
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = { onNext.invoke() }) {
            Text(text = stringResource(id = R.string.label_next))
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