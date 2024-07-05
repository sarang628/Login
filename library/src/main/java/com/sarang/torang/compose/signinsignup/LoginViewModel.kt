package com.sarang.torang.compose.signinsignup

import androidx.lifecycle.ViewModel
import com.sarang.torang.usecase.IsLoginFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    isLoginFlowUseCase: IsLoginFlowUseCase
) : ViewModel() {
    val isLogin = isLoginFlowUseCase.isLogin
}