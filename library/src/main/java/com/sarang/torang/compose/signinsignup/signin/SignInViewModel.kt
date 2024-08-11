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
 * @param email 이메일
 * @param password 비밀번호
 * @param isPasswordVisible 비밀번호 가시성
 * @param isProgress 로그인 요청 시 프로그레스바 표시
 * @param error 에러 팝업 메시지
 * @param emailErrorMessage 이메일 입력 유효성 오류 메시지
 * @param passwordErrorMessage 비밀번호 입력 유효성 오류 메시지
 */
data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isProgress: Boolean = false,
    val error: String? = null,
    val emailErrorMessage: LoginErrorMessage? = null,
    val passwordErrorMessage: LoginErrorMessage? = null,
)

/**
 * ViewModel class for handling sign-in logic.
 * This class uses Hilt for dependency injection.
 *
 * @param emailLoginUseCase 이메일 로그인 UseCase
 * @param verifyEmailFormatUseCase 이메일 형식 검증 UseCase
 * @param verifyPasswordFormatUseCase 비밀번호 형식 검증 UseCase
 */
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val emailLoginUseCase: EmailLoginUseCase,
    private val verifyEmailFormatUseCase: VerifyEmailFormatUseCase,
    private val verifyPasswordFormatUseCase: VerifyPasswordFormatUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(SignInUiState())
        private set

    private var fetchJob: Job? = null

    /**
     * 로그인 요청 함수
     * 이메일과 비밀번호 유효성 검사를 통과하지 못하면 에러 메시지를 설정하고 반환
     * 유효성 검사를 통과하면 로그인 시도를 비동기로 실행
     */
    fun signIn() {
        uiState = uiState.copy(
            emailErrorMessage = verifyEmailFormatUseCase.invoke(uiState.email),
            passwordErrorMessage = verifyPasswordFormatUseCase.invoke(uiState.password)
        )

        if (uiState.emailErrorMessage != null || uiState.passwordErrorMessage != null)
            return

        // 이전 로그인 시도를 취소하고 새로운 로그인 시도 시작
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            clearErrorMsg()
            uiState = uiState.copy(isProgress = true) // 프로그레스바 표시
            try {
                // 이메일 로그인 API 호출
                emailLoginUseCase.invoke(uiState.email, uiState.password)
            } catch (e: Exception) {
                // 로그인 실패 시 에러 메시지 설정
                uiState = uiState.copy(error = e.message.toString())
            } finally {
                // 로그인 시도 후 프로그레스바 감추기
                uiState = uiState.copy(isProgress = false)
            }
        }
    }

    /** 유효성 검사 오류 메시지 초기화 함수 */
    private fun clearValidErrorMessage() {
        uiState = uiState.copy(passwordErrorMessage = null, emailErrorMessage = null)
    }

    /**
     * 이메일 변경 함수
     * @param email 새 이메일 값
     */
    fun onChangeEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    /**
     * 비밀번호 변경 함수
     * @param password 새 비밀번호 값
     */
    fun onChangePassword(password: String) {
        uiState = uiState.copy(password = password)
    }

    /** 이메일 초기화 함수 */
    fun clearEmail() {
        uiState = uiState.copy(email = "")
    }

    /** 에러 메시지 초기화 함수 */
    fun clearErrorMsg() {
        uiState = uiState.copy(error = null)
    }

    /** 비밀번호 가시성 토글 함수 */
    fun onPasswordVisible() {
        uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible)
    }
}