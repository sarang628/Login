package com.sarang.torang.compose.signin

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signin.SignInScreen
import com.sarang.torang.compose.signinsignup.signin.SignInUiState
import com.sarang.torang.data.LoginErrorMessage
import kotlinx.coroutines.test.runTest

@RunWith(AndroidJUnit4::class)
class EmailLoginScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setScreen() {
        composeTestRule.setContent {
            SignInScreen(
                uiState = SignInUiState(
                    email = "sry_ang@naver.com",
                    password = "12345678",
                    emailErrorMessage = LoginErrorMessage.InvalidEmail,
                    passwordErrorMessage = LoginErrorMessage.InvalidPassword
                ),
                onLogin = {},
                onChangeEmail = {},
                onChangePassword = {},
                onClearEmail = { /*TODO*/ },
                onClearErrorMsg = {}
            )
        }
    }

    @Test
    fun displayEmail() = runTest {
        composeTestRule.onNodeWithTag("EmailTextField").assert(hasText("sry_ang@naver.com"))

    }

    @Test
    fun displayPassword() = runTest {
        composeTestRule.onNodeWithTag("PasswordTextField").assert(hasText("••••••••"))
    }

    @Test
    fun checkLoginButton() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.label_login))
            .assertIsDisplayed()
    }

    @Test
    fun checkEmailField() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.label_email))
            .assertIsDisplayed()
    }

    @Test
    fun checkErrorEmailField() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.invalid_email_format))
            .assertIsDisplayed()
    }

    @Test
    fun checkErrorPasswordField() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.invalid_password_format))
            .assertIsDisplayed()
    }

}