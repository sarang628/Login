package com.sarang.torang.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.uistate.LoginUiState

@Composable
fun TorangLogo(uiState: LoginUiState) {
    Column(Modifier.fillMaxWidth())
    {
        /*T O R A N G 제목*/
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = uiState.title, fontSize = 45.sp, fontWeight = FontWeight.Bold)
        }
        /*hit the spot 부제*/
        Spacer(modifier = Modifier.height(30.dp))
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = uiState.subtitle, fontSize = 20.sp)
        }
    }
}

@Preview
@Composable
fun PreviewTorangLogo() {
    TorangLogo(uiState = LoginUiState(title = "T O R A N G", subtitle = "hit the spot"))
}