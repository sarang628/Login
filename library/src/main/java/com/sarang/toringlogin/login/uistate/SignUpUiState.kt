package com.sarang.toringlogin.login.uistate

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val confirmCode: String = "",
    val emailErrorMessage: String? = null
)
