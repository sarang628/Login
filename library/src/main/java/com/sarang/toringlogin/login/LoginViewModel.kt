package com.sarang.toringlogin.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressWarnings("unchecked")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailLoginService: EmailLoginService
) : ViewModel() {

    val uiState = MutableStateFlow(LoginUiState(isLogin = false))
    fun login(emailLogin: EmailLogin) {
        viewModelScope.launch {
            emailLoginService.emailLogin(emailLogin.email, emailLogin.password)
        }
    }
}