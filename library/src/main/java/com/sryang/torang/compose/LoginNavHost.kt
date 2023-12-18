package com.sryang.torang.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sryang.torang.compose.signup.SignUpScreen

@OptIn(ExperimentalMaterial3Api::class)
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
    Scaffold(
        topBar = {
            if (showTopBar) {
                TopAppBar(title = { }, navigationIcon = {
                    IconButton(onClick = { onBack?.invoke() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            ""
                        )
                    }
                })
            }
        }
    ) {
        Box(Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        onSignUp = {
                            navController.navigate("signUp")
                        }, onLookAround = onLookAround,
                        onLogin = onLogin,
                        goEmailLoginDirect = goEmailLoginDirect,
                        showLookAround = true
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

}