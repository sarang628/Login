package com.sarang.torang.compose.signinsignup.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.LoginErrorMessage
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.ValidEmailUseCase
import com.sarang.torang.usecase.ValidPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UiState - what the app says they should see
 */
data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isProgress: Boolean = false,
    val error: String? = null,
    val emailErrorMessage: LoginErrorMessage? = null,
    val passwordErrorMessage: LoginErrorMessage? = null,
)

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val emailLoginService: EmailLoginUseCase,
    private val emailUseCase: ValidEmailUseCase,
    private val passwordUseCase: ValidPasswordUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(SignInUiState())
        private set

    private var fetchJob: Job? = null


    fun login(onLogin: () -> Unit) {
        if (!validateIdPw()) return;
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            clearErrorMsg()
            showProgress(true)
            try {
                emailLoginService.invoke(uiState.email, uiState.password) // 이메일 로그인 API 호출
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
        if (!emailUseCase.invoke(uiState.email)) {
            showEmailErrorMessage(LoginErrorMessage.InvalidEmail)
            isValid = false
        }

        if (!passwordUseCase.invoke(uiState.password)) {
            showPasswordErrorMessage(LoginErrorMessage.InvalidPassword)
            isValid = false
        }
        return isValid
    }


    private fun showPasswordErrorMessage(message: LoginErrorMessage) {
        uiState = uiState.copy(passwordErrorMessage = message)
    }

    private fun showEmailErrorMessage(message: LoginErrorMessage) {
        uiState = uiState.copy(emailErrorMessage = message)
    }

    private fun clearValidErrorMessage() {
        uiState = uiState.copy(passwordErrorMessage = null, emailErrorMessage = null)
    }

    private fun showProgress(b: Boolean) {
        uiState = uiState.copy(isProgress = b)
    }

    private fun showError(error: String) {
        uiState = uiState.copy(error = error)
    }

    fun onChangeEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun onChangePassword(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun clearEmail() {
        uiState = uiState.copy(email = "")
    }

    fun clearErrorMsg() {
        uiState = uiState.copy(error = null)
    }
}