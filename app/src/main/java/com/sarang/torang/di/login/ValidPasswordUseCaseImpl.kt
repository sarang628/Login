package com.sarang.torang.di.login

import com.sarang.torang.usecase.VerifyPasswordFormatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ValidPasswordUseCaseImpl {
    @Provides
    fun providesValidPasswordUseCase(): VerifyPasswordFormatUseCase {
        return object : VerifyPasswordFormatUseCase {
            override fun invoke(password: String): Boolean {
                return password.length >= 5
            }
        }
    }
}