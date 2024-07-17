package com.sarang.torang.compose.signinsignup.signup

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.R
import com.sarang.torang.usecase.CheckEmailDuplicateUseCase
import com.sarang.torang.usecase.ConfirmCodeUseCase
import com.sarang.torang.usecase.VerifyEmailFormatUseCase
import com.sarang.torang.usecase.VerifyPasswordFormatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 회원가입 ui state
 * @param name 이름
 * @param email 이메일
 * @param confirmCode 인증번호
 * @param password 비밀번호
 * @param emailErrorMessage 이메일 입력 오류 메시지
 * @param nameErrorMessage 이름 입력 오류 메시지
 * @param confirmCodeErrorMessage 인증번호 입력 오류 메시지
 * @param alertMessage 인증번호 화면 alert 다이얼로그 메시지
 * @param checkedEmail 이메일 중복 검사 여부
 * @param checkedConfirm 인증번호 검사 여부
 */
data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val confirmCode: String = "",
    val password: String = "",
    val isProgress: Boolean = false,
    @StringRes val emailErrorMessage: Int? = null,
    val nameErrorMessage: Int? = null,
    @StringRes val passwordErrorMessage: Int? = null,
    val confirmCodeErrorMessage: String? = null,
    @StringRes val alertMessage: Int? = null,
    val checkedEmail: Boolean = false,
    val checkedConfirm: Boolean = false,
)

/**
 * 회원가입 view model
 * @param confirmCodeUseCase 인증번호 검증 usecase
 * @param verifyEmailFormatUseCase 이메일 유효성 검증 usecase
 * @param verifyPasswordFormatUseCase 비밀번호 유효성 검증 usecase
 * @param checkEmailDuplicateUseCase 이메일 중복 검사 usecase
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val confirmCodeUseCase: ConfirmCodeUseCase,
    private val verifyEmailFormatUseCase: VerifyEmailFormatUseCase,
    private val verifyPasswordFormatUseCase: VerifyPasswordFormatUseCase,
    private val checkEmailDuplicateUseCase: CheckEmailDuplicateUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(SignUpUiState())
        private set

    private var token = "";

    fun registerEmail() {
        // 이메일 포멧 유효성 체크 로직 실행
        if (!verifyEmailFormatUseCase.invoke(uiState.email)) {
            // 오류 메시지 표시
            uiState = uiState.copy(emailErrorMessage = R.string.invalid_email_format)
        } else {
            // 기존 오류 메시지 초기화
            uiState = uiState.copy(emailErrorMessage = null, isProgress = true)
            viewModelScope.launch {
                try {
                    // 이메일 중복 검사
                    token = checkEmailDuplicateUseCase.checkEmail(
                        uiState.email,
                        uiState.password
                    )
                    // 이메일 중복 검사 통과
                    uiState = uiState.copy(checkedEmail = true)
                } catch (e: Exception) {
                    Log.e("__SignUpViewModel", "registerEmail: ${e.message}")
                    uiState = uiState.copy(emailErrorMessage = R.string.unknown_error)
                } finally {
                    uiState = uiState.copy(isProgress = false)
                }
            }
        }
    }

    fun validPassword(): Boolean {
        return if (!verifyPasswordFormatUseCase.invoke(uiState.password)) {
            uiState = uiState.copy(passwordErrorMessage = R.string.input_at_least_5_characters)
            false
        } else {
            uiState = uiState.copy(passwordErrorMessage = null)
            true
        }
    }

    fun confirmCode() {
        // 인증 번호 검증 시작: 에러 메시지 초기화 및 진행 상태 업데이트
        uiState = uiState.copy(confirmCodeErrorMessage = null, isProgress = true)
        viewModelScope.launch {
            try {
                uiState = uiState.copy(
                    // 인증 번호 검증 로직 수행 및 상태 업데이트
                    checkedConfirm = confirmCodeUseCase.confirmCode(
                        token = token,
                        confirmCode = uiState.confirmCode,
                        name = uiState.name,
                        email = uiState.email,
                        password = uiState.password
                    )
                )
            } catch (e: Exception) {
                // 예외 발생 시 에러 메시지 업데이트
                uiState = uiState.copy(confirmCodeErrorMessage = e.toString())
            } finally {
                // 프로그레스바 숨기기
                uiState = uiState.copy(isProgress = false)
            }
        }
    }

    fun onChangeName(name: String) {
        uiState = uiState.copy(name = name)
    }

    fun clearName() {
        uiState = uiState.copy(name = "")
    }

    fun onChangeEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun clearEmail() {
        uiState = uiState.copy(email = "")
    }

    fun clearConfirmationCode() {
        uiState = uiState.copy(confirmCode = "")
    }

    fun onChangePassword(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun clearPassword() {
        uiState = uiState.copy(password = "")
    }

    fun onChangeConfirmationCode(code: String) {
        uiState = uiState.copy(confirmCode = code)
    }

    fun onAlertConfirm() {
        uiState = uiState.copy(alertMessage = null)
    }

    fun onAlertDismiss() {
        uiState = uiState.copy(alertMessage = null)
    }

    fun onBackConfirm() {
        uiState = uiState.copy(alertMessage = R.string.re_register_email)
    }

    fun clearAlertMessage() {
        uiState = uiState.copy(alertMessage = null)
    }

    fun onMoveBackEmail() {
        uiState = uiState.copy(checkedEmail = false)
    }

    fun checkName(): Boolean {
        if (uiState.name.length <= 2) {
            uiState = uiState.copy(nameErrorMessage = R.string.name_input_error_short)
            return false
        }
        uiState = uiState.copy(nameErrorMessage = null)
        return true
    }
}
