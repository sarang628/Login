package com.sryang.torang.usecase

interface EmailLoginUseCase {
    suspend fun invoke(id: String, email: String)
}