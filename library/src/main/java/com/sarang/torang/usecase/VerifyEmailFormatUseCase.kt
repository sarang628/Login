package com.sarang.torang.usecase

import com.sarang.torang.data.LoginErrorMessage

interface VerifyEmailFormatUseCase {
    fun invoke(email: String) : LoginErrorMessage?
}