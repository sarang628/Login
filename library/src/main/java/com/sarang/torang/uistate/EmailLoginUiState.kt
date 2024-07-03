package com.sarang.torang.uistate

import com.sarang.torang.data.LoginErrorMessage

/**
 * UiState - what the app says they should see
 */
data class EmailLoginUiState(
    val email: String = "",
    val password: String = "",
    val isProgress: Boolean = false,
    val error: String? = null,
    val emailErrorMessage: LoginErrorMessage? = null,
    val passwordErrorMessage: LoginErrorMessage? = null,
)