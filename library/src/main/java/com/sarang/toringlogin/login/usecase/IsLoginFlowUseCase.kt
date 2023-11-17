package com.sarang.toringlogin.login.usecase

import kotlinx.coroutines.flow.StateFlow

interface IsLoginFlowUseCase {
    val isLogin: StateFlow<Boolean>
}