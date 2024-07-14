package com.sarang.torang.compose.signinsignup.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.common.SignCommonTextField

@Composable
internal fun SignUpPassword(
    password: String,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
) {
    var visiblePassword by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { NavBackTopAppBar(onBack = onBack) },
        contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp)
    ) {
        Column(
            Modifier
                .padding(it)
        ) {
            //Create a password
            Text(
                text = stringResource(id = R.string.create_a_password),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            //Create a password with at least 6 letters...
            Text(
                modifier = Modifier.padding(vertical = 6.dp),
                text = stringResource(id = R.string.describe_input_password)
            )
            //Input password
            SignCommonTextField(
                modifier = Modifier
                    .testTag("tfPassword")
                    .padding(vertical = 6.dp),
                label = stringResource(id = R.string.label_password),
                value = password,
                onValueChange = onValueChange,
                placeHolder = stringResource(id = R.string.password_place_holder),
                onClear = { visiblePassword = !visiblePassword },
                errorMessage = errorMessage,
                isPassword = true,
                isPasswordVisual = visiblePassword
            )
            //next button
            Button(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .testTag("btnNext")
                    .fillMaxWidth(),
                onClick = { onNext.invoke() }) {
                Text(text = stringResource(id = R.string.label_next))
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
        onNext = {},
        onBack = {},
        errorMessage = "errorMessage"
    )
}