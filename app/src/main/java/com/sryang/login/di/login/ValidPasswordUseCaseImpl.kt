package com.sryang.login.di.login

import com.sryang.torang.usecase.ValidPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ValidPasswordUseCaseImpl {
    @Provides
    fun providesValidPasswordUseCase(): ValidPasswordUseCase {
        return object : ValidPasswordUseCase {
            override fun invoke(password: String) {
                if (password.length < 5) {
                    throw Exception("5자리 이상 입력해주세요.")
                }
            }
        }
    }
}