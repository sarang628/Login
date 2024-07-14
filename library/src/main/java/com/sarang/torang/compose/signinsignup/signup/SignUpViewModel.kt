package com.sarang.torang.compose.signinsignup.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.R
import com.sarang.torang.usecase.CheckEmailUseCase
import com.sarang.torang.usecase.ConfirmCodeUseCase
import com.sarang.torang.usecase.ValidEmailUseCase
import com.sarang.torang.usecase.ValidPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val confirmCode: String = "",
    val password: String = "",
    val isProgress: Boolean = false,
    val emailErrorMessage: String? = null,
    val nameErrorMessage: Int? = null,
    val passwordErrorMessage: String? = null,
    val confirmCodeErrorMessage: String? = null,
    val alertMessage: String? = null,
    val checkedEmail: Boolean = false,
    val checkedConfirm: Boolean = false,
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val confirmCodeUseCase: ConfirmCodeUseCase,
    private val validEmailUseCase: ValidEmailUseCase,
    private val validPasswordUseCase: ValidPasswordUseCase,
    private val checkEmailUseCase: CheckEmailUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(SignUpUiState())
        private set

    private var token = "";

    fun registerEmail() {
        if (!validEmailUseCase.invoke(uiState.email)) {
            uiState = uiState.copy(emailErrorMessage = "이메일 형식이 아닙니다.")
        } else {
            uiState = uiState.copy(emailErrorMessage = null, isProgress = true)
            viewModelScope.launch {
                try {
                    token = checkEmailUseCase.checkEmail(
                        uiState.email,
                        uiState.password
                    )
                    uiState = uiState.copy(checkedEmail = true)
                } catch (e: Exception) {
                    Log.e("__SignUpViewModel", "registerEmail: ${e.message}")
                    uiState = uiState.copy(emailErrorMessage = e.message)
                } finally {
                    uiState = uiState.copy(isProgress = false)
                }
            }
        }
    }

    fun validPassword(): Boolean {
        return if (!validPasswordUseCase.invoke(uiState.password)) {
            uiState = uiState.copy(passwordErrorMessage = "5자 이상 입력해 주세요")
            false
        } else {
            uiState = uiState.copy(passwordErrorMessage = null)
            true
        }
    }

    fun confirmCode() {
        uiState = uiState.copy(confirmCodeErrorMessage = null, isProgress = true)
        viewModelScope.launch {
            try {
                uiState = uiState.copy(
                    checkedConfirm = confirmCodeUseCase.confirmCode(
                        token = token,
                        confirmCode = uiState.confirmCode,
                        name = uiState.name,
                        email = uiState.email,
                        password = uiState.password
                    )
                )
            } catch (e: Exception) {
                uiState = uiState.copy(confirmCodeErrorMessage = e.toString())
            } finally {
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
        uiState = uiState.copy(alertMessage = "이메일을 다시 등록 하시겠습니까?")
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
