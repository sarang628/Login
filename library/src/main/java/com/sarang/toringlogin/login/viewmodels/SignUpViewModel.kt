package com.sarang.toringlogin.login.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.sarang.toringlogin.login.usecase.EmailLoginService
import com.sarang.toringlogin.login.uistate.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressWarnings("unchecked")
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val emailLoginService: EmailLoginService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

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
            _uiState.value = uiState.value.copy(emailErrorMessage = null)
            return true
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

}