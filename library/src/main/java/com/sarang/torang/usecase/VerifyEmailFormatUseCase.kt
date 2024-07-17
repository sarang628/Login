package com.sarang.torang.usecase

interface VerifyEmailFormatUseCase {
    fun invoke(email: String) : Boolean
}