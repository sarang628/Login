package com.sarang.torang.usecase

interface VerifyPasswordFormatUseCase {
    fun invoke(password: String): Boolean
}