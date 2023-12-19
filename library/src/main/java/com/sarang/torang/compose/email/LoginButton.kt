package com.sarang.torang.compose.email

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.R

@Composable
fun LoginButton(onClick: () -> Unit, progress: Boolean, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        enabled = !progress,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        if (progress)
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(25.dp),
                strokeWidth = 3.dp
            )
        else
            Text(text = stringResource(id = R.string.label_login))
    }
}


@Preview
@Composable
fun PreviewLoginButton() {
    LoginButton(onClick = { /*TODO*/ }, progress = false)
}