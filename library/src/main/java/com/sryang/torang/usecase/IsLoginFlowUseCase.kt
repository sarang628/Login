package com.sryang.torang.usecase

import kotlinx.coroutines.flow.Flow

interface IsLoginFlowUseCase {
    val isLogin: Flow<Boolean>
}