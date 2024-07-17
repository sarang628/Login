package com.sarang.torang.screens.signup

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signup.SignUpName
import com.sarang.torang.compose.signinsignup.signup.SignUpViewModel
import com.sarang.torang.usecase.CheckEmailDuplicateUseCase
import com.sarang.torang.usecase.ConfirmCodeUseCase
import com.sarang.torang.usecase.VerifyEmailFormatUseCase
import com.sarang.torang.usecase.VerifyPasswordFormatUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignUpNameTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> =
        createAndroidComposeRule<ComponentActivity>()

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
    lateinit var validEmailUseCase: VerifyEmailFormatUseCase

    @Inject
    lateinit var validPasswordUseCase: VerifyPasswordFormatUseCase

    @Inject
    lateinit var checkEmailUseCase: CheckEmailDuplicateUseCase

    private lateinit var signUpViewModel: SignUpViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        signUpViewModel = SignUpViewModel(
            confirmCodeUseCase = confirmCodeUseCase,
            verifyEmailFormatUseCase = validEmailUseCase,
            verifyPasswordFormatUseCase = validPasswordUseCase,
            checkEmailDuplicateUseCase = checkEmailUseCase
        )

        // 컴포저블 설정
        composeTestRule.setContent {
            SignUpName(
                name = signUpViewModel.uiState.name,
                errorMessage = signUpViewModel.uiState.nameErrorMessage,
                onClear = signUpViewModel::clearName,
                onValueChange = signUpViewModel::onChangeName,
                onBack = {},
                onNext = signUpViewModel::checkName,
            )
        }
    }

    @Test
    fun emptyNameTest() {
        composeTestRule.onNodeWithTag("btnNext").performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.name_input_error_short))
            .assertIsDisplayed()
    }
}