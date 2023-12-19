package com.sarang.torang.compose.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.R
import com.sarang.torang.compose.email.LoginOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpName(
    name: String,
    onValueChange: (String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
    onNext: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBack.invoke() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.a11y_back)
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
                text = stringResource(id = R.string.what_s_your_name),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            LoginOutlinedTextField(
                label = stringResource(id = R.string.label_full_name),
                value = name,
                onValueChange = onValueChange,
                placeHolder = stringResource(id = R.string.label_full_name),
                onClear = onClear
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(modifier = Modifier.fillMaxWidth(), onClick = { onNext.invoke() }) {
                Text(text = stringResource(id = R.string.label_next))
            }
        }
    }
}

@Preview
@Composable
fun PreviewSignUpName() {
    SignUpName(
        name = "",
        onClear = {},
        onValueChange = {},
        onBack = {},
        onNext = {}
    )
}