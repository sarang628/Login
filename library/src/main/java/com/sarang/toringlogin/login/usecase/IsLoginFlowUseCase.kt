package com.sarang.toringlogin.login.usecase

import kotlinx.coroutines.flow.Flow

interface IsLoginFlowUseCase {
    val isLogin: Flow<Boolean>
}