package com.sarang.torang.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.loginmethod.LoginChooseMethodNavHost
import com.sarang.torang.compose.signup.SignUpNavHost
import com.sarang.torang.screen.login.Screen
import com.sarang.torang.viewmodels.LoginViewModel

@Composable
fun LoginNavHost(
    viewModel: LoginViewModel = hiltViewModel(),
    onSuccessLogin: () -> Unit,
    onLookAround: () -> Unit,
    goEmailLoginDirect: Boolean = false,
    showTopBar: Boolean = false,
    onBack: (() -> Unit)? = null,
    showLookAround: Boolean = true
) {
    val navController = rememberNavController()
    val isLogin by viewModel.isLogin.collectAsState(false)
    NavHost(navController = navController, startDestination = Screen.ChooseMethod.route) {
        composable(Screen.ChooseMethod.route) {
            LoginChooseMethodNavHost(
                isLogin = isLogin,
                onSignUp = { navController.navigate(Screen.SignUp.route) },
                onLookAround = onLookAround,
                onLogin = onSuccessLogin,
                goEmailLoginDirect = goEmailLoginDirect,
                showLookAround = showLookAround,
                showTopBar = showTopBar,
                onBack = onBack
            )
        }
        composable(Screen.SignUp.route) {
            SignUpNavHost(
                onBack = navController::popBackStack,
                signUpSuccess = navController::popBackStack
            )
        }
    }
}