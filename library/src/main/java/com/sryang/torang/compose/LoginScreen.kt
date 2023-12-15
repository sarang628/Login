package com.sryang.torang.compose

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sryang.torang.compose.email.EmailLoginScreen
import com.sryang.torang.compose.signup.SignUpScreen
import com.sryang.torang.uistate.LoginUiState
import com.sryang.torang.viewmodels.LoginViewModel

@Composable
internal fun LoginScreen(
    uiState: LoginUiState,
    isLogin: Boolean,
    onClickFacebookLogin: () -> Unit,   // 페이스북 로그인 클릭
    onSignUp: () -> Unit,               // 회원가입 클릭
    onLookAround: () -> Unit,            // 둘러보기 클릭
    onLogin: () -> Unit
) {
    val navController = rememberNavController()
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    Log.d("_LoginScreen", screenHeightDp.toString())
    Column(
        Modifier
            .verticalScroll(state = rememberScrollState())
    ) {
        TorangLogo(uiState = uiState)
        Spacer(modifier = Modifier.height(130.dp))
        Box(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            NavHost(
                navController = navController,
                startDestination = if (isLogin) "isLogin" else "main"
            ) {
                composable("main") {
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
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Button(onClick = { navController.navigate("email") }) {
                                Text(text = "LOG IN WITH EMAIL")
                            }
                        }
                        /*sign up*/
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Text(text = "Look Around", color = MaterialTheme.colorScheme.primary,
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
                composable("isLogin") {

                }
            }
        }
    }
}

@Composable
fun LoginNavHost(
    viewModel: LoginViewModel = hiltViewModel(),
    onLogin: () -> Unit,
    onLookAround: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLogin by viewModel.isLogin.collectAsState(false)
    LoginNavHost(
        uiState = uiState,
        isLogin = isLogin,
        onLogin = onLogin,
        onLookAround = onLookAround,
        onLogout = {
            viewModel.logout({ })
        }
    )
}

@Composable
fun LoginNavHost(
    uiState: LoginUiState,
    isLogin: Boolean,
    onLogin: () -> Unit,
    onLookAround: () -> Unit,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    Box {
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(uiState = uiState,
                    onClickFacebookLogin = {
                        Toast.makeText(context, "facebook login", Toast.LENGTH_SHORT).show()
                    }, onSignUp = {
                        navController.navigate("signUp")
                    }, onLookAround = onLookAround,
                    onLogin = onLogin,
                    isLogin = isLogin
                )
            }
            composable("signUp") {
                SignUpScreen(onBack = {
                    navController.popBackStack()
                }, signUpSuccess = {
                    navController.popBackStack()
                })
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        uiState = LoginUiState(),
        onClickFacebookLogin = {},
        onSignUp = {},
        onLookAround = {},
        onLogin = {},
        isLogin = false
    )
}