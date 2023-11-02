package com.sarang.toringlogin.login.usecase

import kotlinx.coroutines.flow.StateFlow

interface EmailLoginUseCase {
    suspend fun emailLogin(id: String, email: String)
    suspend fun saveToken(token: String)
    suspend fun logout()

    val isLogin: StateFlow<Boolean>
}