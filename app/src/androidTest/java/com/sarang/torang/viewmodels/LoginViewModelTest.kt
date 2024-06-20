package com.sarang.torang.viewmodels

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.usecase.IsLoginFlowUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LoginViewModelTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var isLoginIsecase: IsLoginFlowUseCase

    @Inject
    lateinit var loginRepository: LoginRepository

    lateinit var loginViewModel: LoginViewModel

    @Before
    fun init() {
        hiltRule.inject()
        loginViewModel = LoginViewModel(isLoginIsecase)
    }

    @Test
    fun isLoginTest() {
        runBlocking {
            loginRepository.emailLogin("sarang628@naver.com", "aaaaa")
            delay(3000)
            //TODO:: islogin test
        }
    }
}