package com.sarang.toringlogin.login

import kotlinx.coroutines.flow.StateFlow

interface EmailLoginService {
    suspend fun emailLogin(id: String, email: String): String
    suspend fun saveToken(token: String)
    suspend fun logout()

    val isLogin: StateFlow<Boolean>
}