package com.sarang.torang.viewmodels

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.signinsignup.signin.SignInViewModel
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.VerifyEmailFormatUseCase
import com.sarang.torang.usecase.VerifyPasswordFormatUseCase
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
class SignInViewModelTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var emailLoginService: EmailLoginUseCase

    @Inject
    lateinit var emailUseCase: VerifyEmailFormatUseCase

    @Inject
    lateinit var passwordUseCase: VerifyPasswordFormatUseCase

    private lateinit var viewModel: SignInViewModel

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = SignInViewModel(
            emailUseCase = emailUseCase,
            emailLoginUseCase = emailLoginService,
            passwordUseCase = passwordUseCase
        )
    }

    @Test
    fun invalidEmailTest() {
        runBlocking {
            viewModel.signIn(onSignIn = {})
            //do business logic
            Assert.assertEquals("Error", viewModel.uiState.emailErrorMessage)
        }
    }

    @Test
    fun invalidPasswordTest() {
        runBlocking {
            viewModel.signIn(onSignIn = {})
            //do business logic
            Assert.assertEquals("Error", viewModel.uiState.passwordErrorMessage)
        }
    }

    @Test
    fun wrongLoginTest() {
        runBlocking {
            viewModel.onChangeEmail("sarang628@naver.com")
            viewModel.onChangePassword("bbbbb")
            viewModel.signIn(onSignIn = {

            })
            //do business logic by api call
            delay(3000)
            Assert.assertEquals("로그인에 실패하였습니다.", viewModel.uiState.error)
        }
    }

    @Test
    fun collectLoginTest() {
        runBlocking {
            viewModel.onChangeEmail("sarang628@naver.com")
            viewModel.onChangePassword("aaaaa")
            viewModel.signIn(onSignIn = {

            })
            //do business logic by api call
            delay(3000)
            Assert.assertEquals(null, viewModel.uiState.error)
        }
    }

}