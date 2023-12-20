package com.sarang.torang.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.LoginTitleUiState
import com.sarang.torang.usecase.IsLoginFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    isLoginFlowUseCase: IsLoginFlowUseCase
) : ViewModel() {
    private val isLoginFlow = isLoginFlowUseCase.isLogin
    private val _isLogin = MutableStateFlow(false)
    val isLogin: StateFlow<Boolean> = _isLogin.asStateFlow()

    init {
        viewModelScope.launch {
            isLoginFlow.collectLatest {
                _isLogin.update { it }
            }
        }
    }
}