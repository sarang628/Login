package com.sarang.torang.compose.signinsignup

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.R
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInSignUpScreenTest {
    @get:Rule(order = 1)
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> =
        createAndroidComposeRule<ComponentActivity>()

    // 테스트에서 사용할 변수들
    var onBack = false
    var onLookAround = false
    var onSignUp = false
    var onEmailLogin = false

    // 리소스 문자열 상수
    private lateinit var loginWithEmailText: String
    private lateinit var dontHaveAccountText: String
    private lateinit var signUpText: String
    private lateinit var lookAroundText: String


    @Before
    fun setScreen() {
        // 변수 초기화
        onBack = false
        onLookAround = false
        onSignUp = false
        onEmailLogin = false

        // 리소스 문자열 초기화
        loginWithEmailText = composeTestRule.activity.getString(R.string.login_with_email)
        dontHaveAccountText = composeTestRule.activity.getString(R.string.dont_have_an_account)
        signUpText = composeTestRule.activity.getString(R.string.sign_up)
        lookAroundText = composeTestRule.activity.getString(R.string.look_around)

        // 컴포저블 설정
        composeTestRule.setContent {
            SignInSignUp(
                showTopBar = true,
                onBack = { onBack = true },
                onLookAround = { onLookAround = true },
                onSignUp = { onSignUp = true },
                isLogin = false,
                onEmailLogin = { onEmailLogin = true }
            )
        }
    }

    @Test
    fun testTextIsDisplayed() {
        // 문구들이 보이는지 확인
        composeTestRule.onNodeWithText(loginWithEmailText).assertIsDisplayed()
        composeTestRule.onNodeWithText(dontHaveAccountText).assertIsDisplayed()
        composeTestRule.onNodeWithText(signUpText).assertIsDisplayed()
        composeTestRule.onNodeWithText(lookAroundText).assertIsDisplayed()
    }

    @Test
    fun testClickActions() {
        // 클릭 이벤트가 있는지 확인
        composeTestRule.onNodeWithText(signUpText).assertHasClickAction()
        composeTestRule.onNodeWithText(loginWithEmailText).assertHasClickAction()
        composeTestRule.onNodeWithText(lookAroundText).assertHasClickAction()

        // 버튼 클릭 이벤트 콜백 동작 확인
        Assert.assertFalse(onBack)
        composeTestRule.onNodeWithTag("btnNavUp").performClick()
        Assert.assertTrue(onBack)

        Assert.assertFalse(onSignUp)
        composeTestRule.onNodeWithText(signUpText).performClick()
        Assert.assertTrue(onSignUp)

        Assert.assertFalse(onEmailLogin)
        composeTestRule.onNodeWithText(loginWithEmailText).performClick()
        Assert.assertTrue(onEmailLogin)

        Assert.assertFalse(onLookAround)
        composeTestRule.onNodeWithText(lookAroundText).performClick()
        Assert.assertTrue(onLookAround)
    }
}