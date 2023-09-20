package com.sryang.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.sarang.toringlogin.login.LoginScreen
import com.sarang.toringlogin.login.LoginViewModel
import com.sryang.torang_repository.session.SessionService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val uiState = loginViewModel.uiState.collectAsState()
            LoginScreen(isLogin = uiState.value.isLogin, onLogin = {
                loginViewModel.login(
                    emailLogin = it
                )
            }, onLogout = {
                loginViewModel.logout()
            })
        }
    }
}