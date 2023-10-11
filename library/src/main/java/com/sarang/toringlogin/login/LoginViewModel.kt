package com.sarang.toringlogin.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressWarnings("unchecked")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailLoginService: EmailLoginService,
) : ViewModel() {

    val uiState = MutableStateFlow(LoginUiState(isLogin = false))

    fun login(emailLogin: EmailLogin) {
        viewModelScope.launch {
            try {
                showProgress(true)
                val result = emailLoginService.emailLogin(emailLogin.email, emailLogin.password)
                emailLoginService.saveToken(result)
                uiState.emit(uiState.value.copy(isLogin = true, isProgressLogin = false))
            } catch (e: java.net.UnknownHostException) {
                showError(e.toString())
                Log.e("LoginViewModel", e.toString())
            } catch (e: Exception) {
                showError(e.toString())
                Log.e("LoginViewModel", e.toString())
            }finally {
                showProgress(false)
            }
        }
    }

    suspend fun showError(error: String) {
        uiState.emit(
            uiState.value.copy(
                error = error
            )
        )
    }

    private suspend fun showProgress(b: Boolean) {
        uiState.emit(
            uiState.value.copy(
                isProgressLogin = b
            )
        )
    }

    fun logout() {
        viewModelScope.launch {
            emailLoginService.logout()
            uiState.emit(uiState.value.copy(isLogin = false))
        }
    }
}