package com.sarang.torang.viewmodels

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signup.SignUpViewModel
import com.sarang.torang.usecase.CheckEmailUseCase
import com.sarang.torang.usecase.ConfirmCodeUseCase
import com.sarang.torang.usecase.ValidEmailUseCase
import com.sarang.torang.usecase.ValidPasswordUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
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

    @get:Rule(order = 1)
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> =
        createAndroidComposeRule<ComponentActivity>()

    private lateinit var errorEmailValid: String
    private lateinit var inputAtLeast5Characters: String

    private lateinit var signUpViewModel: SignUpViewModel

    private val confirmCodeUseCase: ConfirmCodeUseCase
        get() = object : ConfirmCodeUseCase {
            override suspend fun confirmCode(
                token: String,
                confirmCode: String,
                name: String,
                email: String,
                password: String,
            ): Boolean {
                return true
            }
        }

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
            SignUpViewModel(
                confirmCodeUseCase,
                validEmailUseCase,
                validPasswordUseCase,
                emailUseCase
            )

        errorEmailValid = composeTestRule.activity.getString(R.string.error_email_valid)
        inputAtLeast5Characters =
            composeTestRule.activity.getString(R.string.input_at_least_5_characters)
    }

    @Test
    fun invalidEmailTest() {
        runBlocking {
            assertNull(signUpViewModel.uiState.emailErrorMessage)
            signUpViewModel.registerEmail()
            Assert.assertEquals(errorEmailValid, signUpViewModel.uiState.emailErrorMessage)
        }
    }

    @Test
    fun invalidPasswordTest() {
        runBlocking {
            assertNull(signUpViewModel.uiState.passwordErrorMessage)
            signUpViewModel.validPassword()
            Log.d("_SignUpViewModelTest", signUpViewModel.uiState.toString())
            Assert.assertEquals(
                inputAtLeast5Characters,
                signUpViewModel.uiState.passwordErrorMessage
            )
        }
    }

    @Test
    fun duplicateEmailTest() {
        runBlocking {
            assertNull(signUpViewModel.uiState.emailErrorMessage)
            signUpViewModel.onChangeEmail("sarang628@naver.com")
            signUpViewModel.onChangePassword("aaaaa")
            signUpViewModel.registerEmail()
            delay(2000)
            Assert.assertEquals("등록된 이메일 입니다.", signUpViewModel.uiState.emailErrorMessage)
        }
    }

    @Test
    fun confirmCodeTest() {
        runBlocking {
            assertFalse(signUpViewModel.uiState.checkedConfirm)
            signUpViewModel.confirmCode()
            delay(1000)
            assertTrue(signUpViewModel.uiState.checkedConfirm)
        }
    }

}