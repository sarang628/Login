package com.sarang.torang.viewmodels

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.signinsignup.SignInSignUpViewModel
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.usecase.IsLoginFlowUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignInSignUpViewModelTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var isLoginFlowUseCase: IsLoginFlowUseCase

    @Inject
    lateinit var loginRepository: LoginRepository

    private lateinit var loginViewModel: SignInSignUpViewModel

    @Before
    fun init() {
        hiltRule.inject()
        loginViewModel = SignInSignUpViewModel(isLoginFlowUseCase)
    }

    @Test
    fun testIsLogin() = runTest {
        loginRepository.logout()
        Assert.assertEquals(false, loginViewModel.isLogin.getOrAwaitValue())

        // emailLogin 함수 호출
        loginRepository.encEmailLogin("sry_ang@naver.com", "didtkfkd")

        // ViewModel의 isLogin 프로퍼티가 expectedIsLogin와 동일한지 확인
        Assert.assertEquals(true, loginViewModel.isLogin.getOrAwaitValue())

        loginRepository.logout()
        Assert.assertEquals(false, loginViewModel.isLogin.getOrAwaitValue())
    }
}