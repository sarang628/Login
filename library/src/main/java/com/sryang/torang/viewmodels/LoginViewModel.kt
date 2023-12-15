package com.sryang.torang.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.torang.uistate.LoginUiState
import com.sryang.torang.usecase.IsLoginFlowUseCase
import com.sryang.torang.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    isLoginFlowUseCase: IsLoginFlowUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    val isLogin = isLoginFlowUseCase.isLogin
    private val delay = 50L

    init {
        viewModelScope.launch {
            writeTitle("T O R A N G", delay)
            writeSubTitle("Hit the spot", delay)
        }
    }

    private suspend fun writeTitle(title: String, delay: Long) {
        var cursor = 0

        while (cursor < title.length) {
            _uiState.update { it.copy(title = title.substring(0, cursor) + "_") }
            cursor++
            delay(delay)
        }
        _uiState.update { it.copy(title = title) }
    }

    private suspend fun writeSubTitle(title: String, delay: Long) {
        var cursor = 0

        while (cursor < title.length) {
            _uiState.update { it.copy(subtitle = title.substring(0, cursor) + "_") }
            cursor++
            delay(delay)
        }
        _uiState.update { it.copy(subtitle = title) }
    }
}