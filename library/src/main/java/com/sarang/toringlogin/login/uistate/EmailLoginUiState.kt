package com.sarang.toringlogin.login.uistate

data class EmailLoginUiState(
    val email: String = "",
    val password: String = "",
    val isProgress: Boolean = false,
    val error: String? = null,
    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null
)