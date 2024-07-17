package com.sarang.torang.screens.signup

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.SignUpScreen
import com.sarang.torang.compose.signinsignup.signup.SignUpViewModel
import com.sarang.torang.usecase.CheckEmailDuplicateUseCase
import com.sarang.torang.usecase.ConfirmCodeUseCase
import com.sarang.torang.usecase.VerifyEmailFormatUseCase
import com.sarang.torang.usecase.VerifyPasswordFormatUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignUpScreenTest {
    // @formatter:off
    @get:Rule  var hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)  val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> =  createAndroidComposeRule<ComponentActivity>()
    @Inject  lateinit var validEmailUseCase: VerifyEmailFormatUseCase
    @Inject  lateinit var validPasswordUseCase: VerifyPasswordFormatUseCase
    @Inject lateinit var checkEmailUseCase: CheckEmailDuplicateUseCase
    private lateinit var signUpViewModel: SignUpViewModel
    private val confirmCodeUseCase: ConfirmCodeUseCase
        get() = object : ConfirmCodeUseCase {
            override suspend fun confirmCode(token: String, confirmCode: String, name: String, email: String, password: String, ): Boolean {
                return true
            }
        }

    @Before
    fun setScreen() {
        hiltRule.inject()
        signUpViewModel = SignUpViewModel(
            confirmCodeUseCase = confirmCodeUseCase,
            verifyEmailFormatUseCase = validEmailUseCase,
            verifyPasswordFormatUseCase = validPasswordUseCase,
            checkEmailDuplicateUseCase = checkEmailUseCase
        )
        composeTestRule.setContent {
            SignUpScreen(
                signUpViewModel = signUpViewModel,
                onBack = {},
                signUpSuccess = {}
            )
        }
    }
    // @formatter:on

    @Test
    fun signUpFlowTest() = runTest {
        composeTestRule.onNodeWithTag("tfName").performTextInput("test")
        composeTestRule.onNodeWithText("test").assertIsDisplayed()
        composeTestRule.onNodeWithTag("btnNext").performClick()

        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag("tfPassword").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("tfPassword").performTextInput("aaaaa")
        composeTestRule.onNodeWithText("•••••").assertIsDisplayed()
        composeTestRule.onNodeWithTag("btnNext").performClick()

        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag("tfEmail").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("tfEmail").performTextInput("sarang628@gmail.com")
        composeTestRule.onNodeWithText("sarang628@gmail.com").assertIsDisplayed()
        composeTestRule.onNodeWithTag("btnNext").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("tfConfirmCode").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("tfConfirmCode").performTextInput("000011")
        composeTestRule.onNodeWithText("000011").assertIsDisplayed()
        composeTestRule.onNodeWithTag("btnNext").performClick()

        composeTestRule.waitUntil(timeoutMillis = 1000) {
            composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(R.string.success_sign_up))
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.success_sign_up))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("btnNext").performClick()
    }
}