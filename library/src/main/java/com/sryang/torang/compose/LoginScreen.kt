package com.sryang.torang.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sryang.torang.compose.email.EmailLoginScreen
import com.sryang.torang.uistate.LoginUiState
import com.sryang.torang.viewmodels.LoginViewModel

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSignUp: () -> Unit,               // 회원가입 클릭
    onLookAround: () -> Unit,           // 둘러보기 클릭
    onLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLogin by viewModel.isLogin.collectAsState(false)
    LoginScreen(
        uiState = uiState,
        isLogin = isLogin, onSignUp = onSignUp,
        onLookAround = onLookAround,
        onLogin = onLogin
    )
}

@Composable
internal fun LoginScreen(
    uiState: LoginUiState,
    isLogin: Boolean,
    onSignUp: () -> Unit,               // 회원가입 클릭
    onLookAround: () -> Unit,           // 둘러보기 클릭
    onLogin: () -> Unit
) {
    val navController = rememberNavController()
    val height = LocalConfiguration.current.screenHeightDp.dp
    Column(
        Modifier
            .height(height)
            .verticalScroll(state = rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(130.dp))
        TorangLogo(uiState = uiState)
        Spacer(modifier = Modifier.height(130.dp))
        Box(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            if (!isLogin) {
                NavHost(
                    navController = navController,
                    startDestination = "chooseLoginMethod"
                ) {
                    composable("chooseLoginMethod") {
                        ChooseLoginMethod(
                            onEmailLogin = {
                                navController.navigate("emailLogin")
                            }, onSignUp = onSignUp,
                            onLookAround = onLookAround
                        )
                    }
                    composable("emailLogin") {
                        EmailLoginScreen(onLogin = onLogin)
                    }
                }
            }
        }
    }
}

@Composable
fun ChooseLoginMethod(
    onEmailLogin: () -> Unit,
    onSignUp: () -> Unit,               // 회원가입 클릭
    onLookAround: () -> Unit,           // 둘러보기 클릭
) {
    Column {
        /*email 로그인 버튼*/
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { onEmailLogin.invoke() }) {
                Text(text = "LOG IN WITH EMAIL")
            }
        }
        /*sign up*/
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Don't have an account?")
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "Sign up.", color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onSignUp.invoke() })
        }
        /*Look Around*/
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Look Around",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onLookAround.invoke() })
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        uiState = LoginUiState(title = "T O R A N G", subtitle = "hit the spot"),
        isLogin = false,
        onSignUp = {},
        onLookAround = {},
        onLogin = {}
    )
}