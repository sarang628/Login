package com.sarang.torang.di.login

import android.util.Patterns
import com.sarang.torang.usecase.ValidEmailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ValidEmailUseCaseImpl {
    @Provides
    fun provides(): ValidEmailUseCase {
        return object : ValidEmailUseCase {
            override fun invoke(email: String) {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    throw Exception("이메일 형식이 올바르지 않습니다.")
                }
            }
        }
    }
}