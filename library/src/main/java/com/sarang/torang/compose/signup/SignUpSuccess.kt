package com.sarang.torang.compose.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.R

@Composable
internal fun SignUpSuccess(
    onNext: () -> Unit
) {
    Column(
        Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.success_sign_up),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = stringResource(id = R.string.go_sign_in))
        Spacer(modifier = Modifier.height(12.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = { onNext.invoke() }) {
            Text(text = stringResource(id = R.string.label_next))
        }
    }
}


@Preview
@Composable
fun PreviewSignUpSuccess() {
    SignUpSuccess(
        onNext = {}
    )
}