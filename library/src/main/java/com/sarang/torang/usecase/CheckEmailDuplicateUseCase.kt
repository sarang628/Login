package com.sarang.torang.usecase

interface CheckEmailDuplicateUseCase {
    suspend fun checkEmail(email: String, password: String): String
}