package com.sarang.toringlogin.login.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.toringlogin.login.data.EmailLogin
import com.sarang.toringlogin.login.usecase.EmailLoginService
import com.sarang.toringlogin.login.uistate.LoginUiState
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

    fun login(id: String, password: String, onLogin: () -> Unit) {
        viewModelScope.launch {
            try {
                showProgress(true)
                val result = emailLoginService.emailLogin(id, password)
                emailLoginService.saveToken(result)
                uiState.emit(
                    uiState.value.copy(
                        isLogin = true,
                        isProgressLogin = false,
                        error = null
                    )
                )
                onLogin.invoke()
            } catch (e: java.net.UnknownHostException) {
                showError(e.toString())
                Log.e("LoginViewModel", e.toString())
            } catch (e: Exception) {
                showError(e.toString())
                Log.e("LoginViewModel", e.toString())
            } finally {
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

    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            emailLoginService.logout()
            uiState.emit(uiState.value.copy(isLogin = false))
            onLogout.invoke()
        }
    }
}