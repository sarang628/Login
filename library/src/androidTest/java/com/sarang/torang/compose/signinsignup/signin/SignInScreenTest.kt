package com.sarang.torang.compose.signinsignup.signin

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.sarang.torang.R
import com.sarang.torang.data.LoginErrorMessage
import kotlinx.coroutines.test.runTest
import org.junit.Assert

@RunWith(AndroidJUnit4::class)
class SignInScreenTest {
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

    @Test
    fun testSignInScreen_initialState() {
        composeTestRule.setContent {
            SignInScreen(
                uiState = SignInUiState(),
                onLogin = {},
                onChangeEmail = {},
                onChangePassword = {},
                onClearEmail = {},
                onClearErrorMsg = {}
            )
        }

        // 기본 상태에서 이메일과 패스워드 필드가 비어 있는지 확인
        composeTestRule.onNodeWithTag("EmailTextField").assert(hasText(""))
        composeTestRule.onNodeWithTag("PasswordTextField").assert(hasText(""))
    }

    @Test
    fun testSignInScreen_showErrorMessage() {
        composeTestRule.setContent {
            SignInScreen(
                uiState = SignInUiState(error = "Login failed"),
                onLogin = {},
                onChangeEmail = {},
                onChangePassword = {},
                onClearEmail = {},
                onClearErrorMsg = {}
            )
        }

        // 에러 메시지가 표시되는지 확인
        composeTestRule.onNodeWithText("Login failed").assertIsDisplayed()
    }

    @Test
    fun testSignInScreen_loginButtonClicked() {
        var loginClicked = false

        composeTestRule.setContent {
            SignInScreen(
                uiState = SignInUiState(),
                onLogin = { loginClicked = true },
                onChangeEmail = {},
                onChangePassword = {},
                onClearEmail = {},
                onClearErrorMsg = {}
            )
        }

        // 로그인 버튼 클릭 시 onLogin 콜백이 호출되는지 확인
        composeTestRule.onNodeWithTag("LoginBtn").performClick()
        Assert.assertEquals(loginClicked, true)
    }

}