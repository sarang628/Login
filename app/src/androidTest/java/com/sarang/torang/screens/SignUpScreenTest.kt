package com.sarang.torang.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.signinsignup.LoginNavHost
import com.sarang.torang.compose.signinsignup.SignInSignUpScreen
import com.sarang.torang.compose.signinsignup.SignInSignUpViewModel
import com.sarang.torang.compose.signinsignup.SignUpScreen
import com.sarang.torang.compose.signinsignup.signup.SignUpViewModel
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.usecase.IsLoginFlowUseCase
import com.sarang.torang.usecase.SignUpUseCase
import com.sarang.torang.usecase.ValidEmailUseCase
import com.sarang.torang.usecase.ValidPasswordUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import kotlin.math.sign

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignUpScreenTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> =
        createAndroidComposeRule<ComponentActivity>()

    @Inject
    lateinit var signUpUseCase: SignUpUseCase

    @Inject
    lateinit var validEmailUseCase: ValidEmailUseCase

    @Inject
    lateinit var validPasswordUseCase: ValidPasswordUseCase

    private lateinit var signUpViewModel: SignUpViewModel


    @Before
    fun setScreen() {
        hiltRule.inject()
        signUpViewModel = SignUpViewModel(
            signUpUseCase = signUpUseCase,
            validEmailUseCase = validEmailUseCase,
            validPasswordUseCase = validPasswordUseCase
        )

        // 컴포저블 설정
        composeTestRule.setContent {
            SignUpScreen(
                signUpViewModel = signUpViewModel,
                onBack = {},
                signUpSuccess = {}
            )
        }
    }

    @Test
    fun testTextIsDisplayed() = runTest {
        composeTestRule.onNodeWithTag("tfName").performTextInput("test")
        composeTestRule.onNodeWithText("test").assertIsDisplayed()
        composeTestRule.onNodeWithTag("btnNext").performClick()

        composeTestRule.onNodeWithTag("tfPassword").performTextInput("aaaaa")
        composeTestRule.onNodeWithText("•••••").assertIsDisplayed()
        composeTestRule.onNodeWithTag("btnNext").performClick()

        composeTestRule.onNodeWithTag("tfEmail").performTextInput("sarang628@gmail.com")
        composeTestRule.onNodeWithText("sarang628@gmail.com").assertIsDisplayed()
        composeTestRule.onNodeWithTag("btnNext").performClick()
    }
}