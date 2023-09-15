package com.sarang.toringlogin.login

interface EmailLoginService {
    suspend fun emailLogin(id: String, email: String): String
}