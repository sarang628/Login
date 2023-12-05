package com.sryang.torang.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.torang.uistate.EmailLoginUiState
import com.sryang.torang.usecase.EmailLoginUseCase
import com.sryang.torang.usecase.IsLoginFlowUseCase
import com.sryang.torang.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailLoginViewModel @Inject constructor(
    private val emailLoginService: EmailLoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val isLoginFlowUseCase: IsLoginFlowUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmailLoginUiState())
    var uiState = _uiState.asStateFlow()
    val isLogin = isLoginFlowUseCase.isLogin

    fun login(id: String, password: String, onLogin: () -> Unit) {
        // ID 패스워드 둘 다 검사를 우선 해야 해서 변수로 결괏값 입력 받아 처리
        val isValidLogin = validateEmail(id)
        val isValidPassword = validatePassword(password)
        if (!(isValidLogin && isValidPassword)) // 유효성 검사 실패시 API 호출하지 않기
            return;

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

    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            logoutUseCase.invoke()
            onLogout.invoke()
        }
    }

    private fun validateEmail(email: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = uiState.value.copy(emailErrorMessage = "이메일 형식이 올바르지 않습니다.")
            return false
        } else {
            _uiState.value = uiState.value.copy(emailErrorMessage = null)
        }
        return true
    }

    private fun validatePassword(password: String): Boolean {
        if (password.length < 5) {
            _uiState.value = uiState.value.copy(passwordErrorMessage = "비밀번호는 최소 5자리 이상입니다.")
            return false
        } else {
            _uiState.value = uiState.value.copy(passwordErrorMessage = null)
        }
        return true
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