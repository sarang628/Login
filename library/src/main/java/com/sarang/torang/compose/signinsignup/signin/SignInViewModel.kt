package com.sarang.torang.compose.signinsignup.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.LoginErrorMessage
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.VerifyEmailFormatUseCase
import com.sarang.torang.usecase.VerifyPasswordFormatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UiState - what the app says they should see
 */
data class SignInUiState(
    val email: String = "", // 이메일
    val password: String = "", // 비밀번호
    val isProgress: Boolean = false, // 로그인 요청 시 프로그레스바 표시
    val error: String? = null, // 에러 팝업 메시지
    val emailErrorMessage: LoginErrorMessage? = null, // 이메일 입력 유효성 오류 메시지
    val passwordErrorMessage: LoginErrorMessage? = null, // 비밀번호 입력 유효성 오류 메시지
)

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val emailLoginUseCase: EmailLoginUseCase,
    private val emailUseCase: VerifyEmailFormatUseCase,
    private val passwordUseCase: VerifyPasswordFormatUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(SignInUiState())
        private set

    private var fetchJob: Job? = null


    /** 로그인 요청 */
    fun signIn(onSignIn: () -> Unit) {
        if (!validateIdPw()) return;
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            clearErrorMsg()
            uiState = uiState.copy(isProgress = true) // 프로그래스바 표시
            try {
                emailLoginUseCase.invoke(uiState.email, uiState.password) // 이메일 로그인 API 호출
                onSignIn.invoke()
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message.toString()) // 에러메시지 표시
            } finally {
                uiState = uiState.copy(isProgress = false) // 프로그래스바 감추기
            }
        }
    }

    /**
     * 유효성 검사
     */
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