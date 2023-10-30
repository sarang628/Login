package com.sarang.toringlogin.login.uistate

data class LoginUiState(
    val isLogin: Boolean,
    val isProgressLogin: Boolean = false,
    val error: String? = null
)
