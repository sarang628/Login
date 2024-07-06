package com.sarang.torang.compose.signin

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.signinsignup.signin.SignInScreen
import com.sarang.torang.compose.signinsignup.signin.SignInUiState
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

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