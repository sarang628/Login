package com.sarang.torang.usecase

interface ConfirmCodeUseCase {
    suspend fun confirmCode(
        token: String,
        confirmCode: String,
        name: String,
        email: String,
        password: String
    ): Boolean
}