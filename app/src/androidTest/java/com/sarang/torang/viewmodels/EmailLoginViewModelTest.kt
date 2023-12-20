package com.sarang.torang.viewmodels

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.ValidEmailUseCase
import com.sarang.torang.usecase.ValidPasswordUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class EmailLoginViewModelTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var emailLoginService: EmailLoginUseCase

    @Inject
    lateinit var emailUseCase: ValidEmailUseCase

    @Inject
    lateinit var passwordUseCase: ValidPasswordUseCase

    private lateinit var viewModel: EmailLoginViewModel

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = EmailLoginViewModel(
            emailUseCase = emailUseCase,
            emailLoginService = emailLoginService,
            passwordUseCase = passwordUseCase
        )
    }

    @Test
    fun invalidEmail() {
        runBlocking {
            viewModel.login("aaa", "aaa", onLogin = {})
            //do business logic
            Assert.assertEquals("이메일 형식이 올바르지 않습니다.", viewModel.uiState.value.emailErrorMessage)
        }
    }

    @Test
    fun invalidPassword() {
        runBlocking {
            viewModel.login("aaa", "aaa", onLogin = {})
            //do business logic
            Assert.assertEquals("5자리 이상 입력해주세요.", viewModel.uiState.value.passwordErrorMessage)
        }
    }

    @Test
    fun wrongLogin() {
        runBlocking {
            viewModel.login("sarang628@naver.com", "bbbbb", onLogin = {

            })
            //do business logic by api call
            delay(3000)
            Assert.assertEquals("로그인에 실패하였습니다.", viewModel.uiState.value.error)
        }
    }

}