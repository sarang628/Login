package com.sryang.torang.usecase

interface SignUpUseCase {
    suspend fun confirmCode(
        token: String,
        confirmCode: String,
        name: String,
        email: String,
        password: String
    ): Boolean

    suspend fun checkEmail(email: String, password: String): String
}