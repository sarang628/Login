package com.sarang.toringlogin.login

import kotlinx.coroutines.flow.MutableStateFlow

interface EmailLoginService {
    suspend fun emailLogin(id: String, email: String): String
    suspend fun saveToken(token: String)
    suspend fun logout()

    val isLogin: MutableStateFlow<Boolean>
}