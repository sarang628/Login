package com.sarang.toringlogin.login

data class LoginUiState(
    val isLogin: Boolean,
    val isProgressLogin: Boolean = false,
    val error: String? = null
)
