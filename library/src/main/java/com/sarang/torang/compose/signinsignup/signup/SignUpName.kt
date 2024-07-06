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
import com.sarang.torang.compose.signinsignup.common.SignInTextField

@Composable
internal fun SignUpName(
    name: String,
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
            text = stringResource(id = R.string.what_s_your_name),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        SignInTextField(
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