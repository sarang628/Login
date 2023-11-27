package com.sryang.torang.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.torang.uistate.SignUpUiState
import com.sryang.torang.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressWarnings("unchecked")
@HiltViewModel
class SignUpViewModel @Inject constructor(
    val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()
    var token = "";

    fun onChangeName(name: String) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(name = name)
        }
    }

    fun clearName() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(name = "")
        }
    }

    fun onChangeEmail(email: String) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(email = email)
        }
    }

    fun clearEmail() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(email = "")
        }
    }

    suspend fun registerEmail(): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(uiState.value.email).matches()) {
            _uiState.value = uiState.value.copy(emailErrorMessage = "이메일 형식이 아닙니다.")
            return false
        } else {
            _uiState.value = uiState.value.copy(emailErrorMessage = null, isProgress = true)
            try {
                token = signUpUseCase.checkEmail(uiState.value.email, uiState.value.password)
                return true
            } catch (e: Exception) {
                _uiState.value = uiState.value.copy(emailErrorMessage = e.message)
            } finally {
                _uiState.value = uiState.value.copy(isProgress = false)
            }
            return false
        }
    }

    fun onChangeConfirmationCode(code: String) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(confirmCode = code)
        }
    }

    fun clearConfirmationCode() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(confirmCode = "")
        }
    }

    fun onChangePassword(it: String) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(password = it)
        }
    }

    fun clearPassword() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(password = "")
        }
    }

    suspend fun validPassword(): Boolean {
        if (uiState.value.password.length < 5) {
            _uiState.value = uiState.value.copy(passwordErrorMessage = "5자 이상 입력해 주세요")
            return false
        } else {
            _uiState.value = uiState.value.copy(passwordErrorMessage = null)
            return true
        }
    }

    suspend fun confirmCode(): Boolean {
        try {
            _uiState.value = uiState.value.copy(confirmCodeErrorMessage = null, isProgress = true)
            return signUpUseCase.confirmCode(
                token = token,
                confirmCode = uiState.value.confirmCode,
                name = uiState.value.name,
                email = uiState.value.email,
                password = uiState.value.password
            )
        } catch (e: Exception) {
            _uiState.value = uiState.value.copy(confirmCodeErrorMessage = e.toString())
        } finally {
            _uiState.value = uiState.value.copy(isProgress = false)
        }
        return false
    }

}