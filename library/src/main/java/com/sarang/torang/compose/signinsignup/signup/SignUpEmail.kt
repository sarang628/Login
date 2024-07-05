package com.sarang.torang.compose.signinsignup.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signin.SignInTextField

@Composable
internal fun SignUpEmail(
    email: String,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.what_s_your_email),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = stringResource(id = R.string.describe_input_email))
        Spacer(modifier = Modifier.height(12.dp))
        SignInTextField(
            label = stringResource(id = R.string.label_email),
            value = email,
            onValueChange = onValueChange,
            placeHolder = stringResource(id = R.string.label_email),
            onClear = onClear,
            errorMessage = if (errorMessage != null) stringResource(id = R.string.error_email_valid) else null
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = onNext::invoke) {
            Text(text = stringResource(id = R.string.label_next))
        }
    }
}


@Preview
@Composable
fun PreviewSignUp() {
    SignUpEmail(
        email = "",
        errorMessage = "aa",
        onClear = {},
        onValueChange = {},
        onBack = {},
        onNext = {}
    )
}