package com.sarang.torang.usecase

import com.sarang.torang.data.LoginErrorMessage

interface VerifyPasswordFormatUseCase {
    fun invoke(password: String): LoginErrorMessage?
}