package com.sarang.torang.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.uistate.LoginTitleUiState
import com.sarang.torang.viewmodels.TorangLogoViewModel

@Composable
fun TorangLogo(
    viewModel: TorangLogoViewModel = hiltViewModel()
) {
    val uiState by viewModel.loginTitleUiState.collectAsState()
    TorangLogo(uiState = uiState)
}

@Composable
fun TorangLogo(uiState: LoginTitleUiState) {
    Column {
        Spacer(modifier = Modifier.height(130.dp))
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
        Spacer(modifier = Modifier.height(130.dp))
    }
}

@Preview
@Composable
fun PreviewTorangLogo() {
    TorangLogo(uiState = LoginTitleUiState(title = "T O R A N G", subtitle = "hit the spot"))
}