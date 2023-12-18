package com.sryang.torang.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sryang.torang.compose.signup.SignUpScreen

@Composable
fun LoginNavHost(
    onLogin: () -> Unit,
    onLookAround: () -> Unit,
    goEmailLoginDirect: Boolean = false,
    showTopBar: Boolean = false,
    onBack: (() -> Unit)? = null,
    showLookAround: Boolean = true
) {
    val navController = rememberNavController()
    Box() {
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    onSignUp = {
                        navController.navigate("signUp")
                    }, onLookAround = onLookAround,
                    onLogin = onLogin,
                    goEmailLoginDirect = goEmailLoginDirect,
                    showLookAround = showLookAround,
                    showTopBar = showTopBar,
                    onBack = {
                        onBack?.invoke()
                    }
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