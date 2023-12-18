package com.sryang.torang.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sryang.torang.compose.signup.SignUpScreen

@Composable
fun LoginNavHost(
    onLogin: () -> Unit,
    onLookAround: () -> Unit,
    goEmailLoginDirect: Boolean = false
) {
    val navController = rememberNavController()
    Box {
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    onSignUp = {
                        navController.navigate("signUp")
                    }, onLookAround = onLookAround,
                    onLogin = onLogin,
                    goEmailLoginDirect = goEmailLoginDirect
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