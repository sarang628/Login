package com.sarang.torang.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.sarang.torang.uistate.SignUpUiState
import com.sarang.torang.usecase.SignUpUseCase
import com.sarang.torang.usecase.ValidEmailUseCase
import com.sarang.torang.usecase.ValidPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val validEmailUseCase: ValidEmailUseCase,
    private val validPasswordUseCase: ValidPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()
    private var token = "";

    suspend fun registerEmail(): Boolean {
        if (!validEmailUseCase.invoke(uiState.value.email)) {
            _uiState.update { it.copy(emailErrorMessage = "이메일 형식이 아닙니다.") }
            return false
        } else {
            _uiState.update { it.copy(emailErrorMessage = null, isProgress = true) }
            try {
                token = signUpUseCase.checkEmail(
                    uiState.value.email,
                    uiState.value.password
                )
                return true
            } catch (e: Exception) {
                _uiState.update { it.copy(emailErrorMessage = e.message) }
            } finally {
                _uiState.update { it.copy(isProgress = false) }
            }
            return false
        }
    }

    fun validPassword(): Boolean {
        return if (!validPasswordUseCase.invoke(uiState.value.password)) {
            _uiState.update { it.copy(passwordErrorMessage = "5자 이상 입력해 주세요") }
            false
        } else {
            _uiState.update { it.copy(passwordErrorMessage = null) }
            true
        }
    }

    suspend fun confirmCode(): Boolean {
        try {
            _uiState.update { it.copy(confirmCodeErrorMessage = null, isProgress = true) }
            return signUpUseCase.confirmCode(
                token = token,
                confirmCode = uiState.value.confirmCode,
                name = uiState.value.name,
                email = uiState.value.email,
                password = uiState.value.password
            )
        } catch (e: Exception) {
            _uiState.update { it.copy(confirmCodeErrorMessage = e.toString()) }
        } finally {
            _uiState.update { it.copy(isProgress = false) }
        }
        return false
    }

    fun onChangeName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun clearName() {
        _uiState.update { it.copy(name = "") }
    }

    fun onChangeEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun clearEmail() {
        _uiState.update { it.copy(email = "") }
    }

    fun clearConfirmationCode() {
        _uiState.update { it.copy(confirmCode = "") }
    }

    fun onChangePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun clearPassword() {
        _uiState.update { it.copy(password = "") }
    }

    fun onChangeConfirmationCode(code: String) {
        _uiState.update { it.copy(confirmCode = code) }
    }
}