package com.sarang.torang.data

import androidx.annotation.StringRes
import com.sarang.torang.R

sealed class LoginErrorMessage(@StringRes val resId: Int) {
    object InvalidEmail : LoginErrorMessage(R.string.invalid_email_format)
    object InvalidPassword : LoginErrorMessage(R.string.invalid_password_format)
}
