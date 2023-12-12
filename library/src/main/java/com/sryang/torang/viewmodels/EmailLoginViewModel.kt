package com.sryang.torang.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.torang.uistate.EmailLoginUiState
import com.sryang.torang.usecase.EmailLoginUseCase
import com.sryang.torang.usecase.ValidEmailUseCase
import com.sryang.torang.usecase.ValidPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailLoginViewModel @Inject constructor(
    private val emailLoginService: EmailLoginUseCase,
    private val emailUseCase: ValidEmailUseCase,
    private val passwordUseCase: ValidPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmailLoginUiState())
    var uiState = _uiState.asStateFlow()

    fun login(id: String, password: String, onLogin: () -> Unit) {
        // ID 패스워드 둘 다 검사를 우선 해야 해서 변수로 결괏값 입력 받아 처리
        var isValid = true
        try {
            _uiState.update { it.copy(emailErrorMessage = null) }
            emailUseCase.invoke(id)
        } catch (e: Exception) {
            _uiState.update { it.copy(emailErrorMessage = e.message) }
            isValid = false
        }

        try {
            _uiState.update { it.copy(passwordErrorMessage = null) }
            passwordUseCase.invoke(password)
        } catch (e: Exception) {
            _uiState.update { it.copy(passwordErrorMessage = e.message) }
            isValid = false
        }

        if (!isValid)
            return

        viewModelScope.launch {
            try {
                showProgress(true)
                clearErrorMsg()
                emailLoginService.invoke(id, password) // 이메일 로그인 API 호출
                onLogin.invoke()
            } catch (e: Exception) {
                showError(e.message!!)
            } finally {
                showProgress(false)
            }
        }
    }

    private fun showProgress(b: Boolean) {
        _uiState.update { it.copy(isProgress = b) }
    }

    private fun showError(error: String) {
        _uiState.update { it.copy(error = error) }
    }

    fun onChangeEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onChangePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun clearEmail() {
        _uiState.update { it.copy(email = "") }
    }

    fun clearPassword() {
        _uiState.update { it.copy(password = "") }
    }

    fun clearErrorMsg() {
        _uiState.update { it.copy(error = null) }
    }
}