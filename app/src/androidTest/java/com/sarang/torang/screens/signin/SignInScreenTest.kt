package com.sarang.torang.screens.signin

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signin.SignInScreen
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.VerifyEmailFormatUseCase
import com.sarang.torang.usecase.VerifyPasswordFormatUseCase
import com.sarang.torang.compose.signinsignup.signin.SignInViewModel
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
class SignInScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Inject
    lateinit var emailLoginService: EmailLoginUseCase

    @Inject
    lateinit var emailUseCase: VerifyEmailFormatUseCase

    @Inject
    lateinit var passwordUseCase: VerifyPasswordFormatUseCase

    private lateinit var signInViewModel: SignInViewModel

    private lateinit var ilCorrectEmailFormat: String
    private lateinit var ilCorrectPasswordFormat: String

    @Before
    fun init() {
        hiltRule.inject()
        signInViewModel = SignInViewModel(
            verifyEmailFormatUseCase = emailUseCase,
            emailLoginUseCase = emailLoginService,
            verifyPasswordFormatUseCase = passwordUseCase
        )

        composeTestRule.setContent {
            SignInScreen(
                viewModel = signInViewModel
            )
        }

        ilCorrectEmailFormat = composeTestRule.activity.getString(R.string.invalid_email_format)
        ilCorrectPasswordFormat = composeTestRule.activity.getString(R.string.invalid_password_format)
    }

    @Test
    fun loginTest() = runTest {
        composeTestRule
            .onNodeWithText("Email")
            .performTextInput("sarang628@naver.com")
        composeTestRule
            .onNodeWithText("Password")
            .performTextInput("aaaaa")

        composeTestRule.onNodeWithText("Log in").performClick()
    }

    @Test
    fun loginWrongFormatTest() {
        composeTestRule.onNodeWithText("Log in").performClick()

        composeTestRule.onNodeWithText(ilCorrectEmailFormat).assertIsDisplayed()
        composeTestRule.onNodeWithText(ilCorrectPasswordFormat).assertIsDisplayed()
    }
}
