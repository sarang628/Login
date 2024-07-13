package com.sarang.torang.usecase

import kotlinx.coroutines.flow.Flow

/**
 * 로그인 여부를 관찰하는 UseCase
 */
interface IsLoginFlowUseCase {
    val isLogin: Flow<Boolean> // 로그인 여부
}