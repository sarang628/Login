package com.sarang.torang.usecase

interface EmailLoginUseCase {
    suspend fun invoke(id: String, email: String)
}