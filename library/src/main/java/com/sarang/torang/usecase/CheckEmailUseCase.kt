package com.sarang.torang.usecase

interface CheckEmailUseCase {
    suspend fun checkEmail(email: String, password: String): String
}