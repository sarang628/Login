package com.sryang.login.di

import com.example.torang_core.repository.LoginRepository
import com.example.torangrepository.LoginRepositoryImpl
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