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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sryang.torang.compose.email.EmailLoginScreen
import com.sryang.torang.viewmodels.LoginViewModel

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onClickFacebookLogin: () -> Unit,   // 페이스북 로그인 클릭
    onSignUp: () -> Unit,               // 회원가입 클릭
    onLookAround: () -> Unit,            // 둘러보기 클릭
    onLogin: () -> Unit
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()
    val isLogin by viewModel.isLogin.collectAsState(false)
    Column(
        Modifier
            .verticalScroll(state = rememberScrollState())
    ) {
        TorangLogo(uiState = uiState)
        Spacer(modifier = Modifier.height(130.dp))
        Box(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            if (!isLogin) {
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        Column {
                            /*facebook 로그인 버튼*/
                            /*Spacer(modifier = Modifier.height(150.dp))
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                Button(onClick = { onClickFacebookLogin.invoke() }) {
                                    Text(text = "LOG IN WITH FACEBOOK")
                                }
                            }*/
                            /*email 로그인 버튼*/
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(onClick = { navController.navigate("email") }) {
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
                    composable("email") {
                        EmailLoginScreen(onLogin = onLogin)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        onClickFacebookLogin = {},
        onSignUp = {},
        onLookAround = {},
        onLogin = {}
    )
}