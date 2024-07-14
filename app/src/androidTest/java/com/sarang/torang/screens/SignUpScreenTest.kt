package com.sarang.torang.screens

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
import com.sarang.torang.usecase.CheckEmailUseCase
import com.sarang.torang.usecase.ConfirmCodeUseCase
import com.sarang.torang.usecase.ValidEmailUseCase
import com.sarang.torang.usecase.ValidPasswordUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignUpScreenTest {
    @get:Rule
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
    lateinit var validEmailUseCase: ValidEmailUseCase

    @Inject
    lateinit var validPasswordUseCase: ValidPasswordUseCase

    @Inject
    lateinit var checkEmailUseCase: CheckEmailUseCase

    private lateinit var signUpViewModel: SignUpViewModel


    @Before
    fun setScreen() {
        hiltRule.inject()
        signUpViewModel = SignUpViewModel(
            confirmCodeUseCase = confirmCodeUseCase,
            validEmailUseCase = validEmailUseCase,
            validPasswordUseCase = validPasswordUseCase,
            checkEmailUseCase = checkEmailUseCase
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
    fun signUpTest() = runTest {
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

        composeTestRule.waitUntil(timeoutMillis = 5000) {
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