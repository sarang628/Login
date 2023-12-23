package com.sarang.torang.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.EmailLoginUiState
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.ValidEmailUseCase
import com.sarang.torang.usecase.ValidPasswordUseCase
import com.sarang.torang.util.Encrypt.encrypt
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

    fun login(onLogin: () -> Unit) {
        if (!validateIdPw())
            return;

        viewModelScope.launch {
            try {
                showProgress(true)
                clearErrorMsg()
                emailLoginService.invoke(
                    uiState.value.email,
                    encrypt(uiState.value.password)
                ) // 이메일 로그인 API 호출
                onLogin.invoke()
            } catch (e: Exception) {
                showError(e.message!!)
            } finally {
                showProgress(false)
            }
        }
    }

    private fun validateIdPw(): Boolean {
        clearValidErrorMessage()
        var isValid = true
        if (!emailUseCase.invoke(uiState.value.email)) {
            showEmailErrorMessage("Error")
            isValid = false
        }

        if (!passwordUseCase.invoke(uiState.value.password)) {
            showPasswordErrorMessage("Error")
            isValid = false
        }
        return isValid
    }


    private fun showPasswordErrorMessage(message: String?) {
        _uiState.update { it.copy(passwordErrorMessage = message.toString()) }
    }

    private fun showEmailErrorMessage(message: String?) {
        _uiState.update { it.copy(emailErrorMessage = message.toString()) }
    }

    private fun clearValidErrorMessage() {
        _uiState.update { it.copy(passwordErrorMessage = null, emailErrorMessage = null) }
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