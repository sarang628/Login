package com.sryang.login.di

import com.sryang.torang_repository.repository.LoginRepository
import com.sryang.torang_repository.repository.impl.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginRepositoryModule {
    @Binds
    abstract fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl) : LoginRepository
}