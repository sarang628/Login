package com.sarang.toringlogin.login.uistate

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val confirmCode: String = "",
    val password: String = "",
    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null
)
