package com.sarang.toringlogin.login.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.toringlogin.login.uistate.EmailLoginUiState
import com.sarang.toringlogin.login.usecase.EmailLoginService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailLoginViewModel @Inject constructor(
    private val emailLoginService: EmailLoginService,
) : ViewModel() {

    private var _uiState = MutableStateFlow(EmailLoginUiState())
    var uiState = _uiState.asStateFlow()

    val isLogin = emailLoginService.isLogin

    fun login(id: String, password: String) {

        var isValidate = true

        if (!Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
            _uiState.value = uiState.value.copy(emailErrorMessage = "이메일 형식이 올바르지 않습니다.")
            isValidate = false
        } else {
            _uiState.value = uiState.value.copy(emailErrorMessage = null)
        }

        if (password.length < 5) {
            _uiState.value = uiState.value.copy(passwordErrorMessage = "비밀번호는 최소 5자리 이상입니다.")
            isValidate = false
        } else {
            _uiState.value = uiState.value.copy(passwordErrorMessage = null)
        }

        if (!isValidate) // 유효성 검사 실패시 API 호출하지 않기
            return;

        viewModelScope.launch {
            try {
                showProgress(true)
                emailLoginService.emailLogin(id, password) // 이메일 로그인 API 호출
                _uiState.emit(
                    uiState.value.copy(error = null)
                )
            } catch (e: java.net.UnknownHostException) {
                showError(e.toString())
                Log.e("LoginViewModel", e.toString())
            } catch (e: Exception) {
                showError(e.toString())
                Log.e("LoginViewModel", e.toString())
            } finally {
                showProgress(false)
            }
        }
    }

    private suspend fun showProgress(b: Boolean) {
        _uiState.emit(
            uiState.value.copy(
                isProgress = b
            )
        )
    }

    suspend fun showError(error: String) {
        _uiState.emit(
            uiState.value.copy(
                error = error
            )
        )
    }

    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            emailLoginService.logout()
            onLogout.invoke()
        }
    }

    fun onChangeEmail(it: String) {
        _uiState.value = uiState.value.copy(email = it)
    }

    fun onChangePassword(it: String) {
        _uiState.value = uiState.value.copy(password = it)
    }

    fun clearEmail() {
        _uiState.value = uiState.value.copy(email = "")
    }

    fun clearPassword() {
        _uiState.value = uiState.value.copy(password = "")
    }


}