package com.sarang.toringlogin.login.usecase

interface EmailLoginUseCase {
    suspend fun invoke(id: String, email: String)
}