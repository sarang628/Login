package com.sarang.toringlogin.login.usecase

interface SignUpUseCase {
    suspend fun confirmCode(confirmCode: String)
}