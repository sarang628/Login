package com.sarang.toringlogin.login.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.toringlogin.login.usecase.EmailLoginService
import com.sarang.toringlogin.login.uistate.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressWarnings("unchecked")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailLoginService: EmailLoginService,
) : ViewModel() {

    val uiState = MutableStateFlow(LoginUiState(isLogin = false))

}