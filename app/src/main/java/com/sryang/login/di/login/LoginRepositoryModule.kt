package com.sryang.login.di.login

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.sarang.toringlogin.login.EmailLoginService
import com.sryang.torang_repository.services.impl.LoginService
import com.sryang.torang_repository.services.impl.LoginServiceProvider
import com.sryang.torang_repository.services.impl.getLoginService
import com.sryang.torang_repository.session.SessionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginRepositoryModule {
    @Singleton
    @Provides
    fun emailLoginService(loginService: LoginService): EmailLoginService {
        return object : EmailLoginService {
            override suspend fun emailLogin(id: String, email: String): String {
                return loginService.emailLogin(id, email).token
            }
        }
    }

    @Singleton
    @Provides
    fun loginService(@ApplicationContext context: Context): LoginService {
        return getLoginService(context)
    }

    @Singleton
    @Provides
    fun sessionService(@ApplicationContext context: Context) : SessionService{
        return SessionService(context)
    }
}