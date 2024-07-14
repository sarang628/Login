package com.sarang.torang.viewmodels

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.signinsignup.signup.SignUpViewModel
import com.sarang.torang.usecase.CheckEmailUseCase
import com.sarang.torang.usecase.ConfirmCodeUseCase
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
class SignUpViewModelTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var signUpViewModel: SignUpViewModel

    @Inject
    lateinit var signUpUseCase: ConfirmCodeUseCase

    @Inject
    lateinit var emailUseCase: CheckEmailUseCase

    @Inject
    lateinit var validEmailUseCase: ValidEmailUseCase

    @Inject
    lateinit var validPasswordUseCase: ValidPasswordUseCase

    @Before
    fun init() {
        hiltRule.inject()
        signUpViewModel =
            SignUpViewModel(signUpUseCase, validEmailUseCase, validPasswordUseCase, emailUseCase)
    }

    @Test
    fun invalidEmailTest() {
        runBlocking {
            signUpViewModel.registerEmail()
            Log.d("_SignUpViewModelTest", signUpViewModel.uiState.toString())
            Assert.assertEquals("이메일 형식이 아닙니다.", signUpViewModel.uiState.emailErrorMessage)
        }
    }

    @Test
    fun invalidPasswordTest() {
        runBlocking {
            signUpViewModel.validPassword()
            Log.d("_SignUpViewModelTest", signUpViewModel.uiState.toString())
            Assert.assertEquals(
                "5자 이상 입력해 주세요",
                signUpViewModel.uiState.passwordErrorMessage
            )
        }
    }

    @Test
    fun duplicateEmailTest() {
        runBlocking {
            signUpViewModel.onChangeEmail("sarang628@naver.com")
            signUpViewModel.onChangePassword("aaaaa")
            signUpViewModel.registerEmail()
            delay(2000)
            Assert.assertEquals("등록된 이메일 입니다.", signUpViewModel.uiState.emailErrorMessage)
        }
    }

}