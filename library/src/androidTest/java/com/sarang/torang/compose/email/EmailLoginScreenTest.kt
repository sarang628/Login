package com.sarang.torang.compose.email

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.uistate.EmailLoginUiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.sarang.torang.R
import com.sarang.torang.data.LoginErrorMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest

@RunWith(AndroidJUnit4::class)
class EmailLoginScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setScreen() {
        composeTestRule.setContent {
            EmailLoginScreen(
                uiState = EmailLoginUiState(
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
        composeTestRule.onNodeWithText("sry_ang@naver.com")
            .assertExists()
    }

    @Test
    fun displayPassword() = runTest {
        composeTestRule.onNodeWithText("••••••••")
            .assertExists()
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