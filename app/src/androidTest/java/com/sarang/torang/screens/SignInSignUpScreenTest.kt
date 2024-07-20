package com.sarang.torang.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.SignInSignUpScreen
import com.sarang.torang.compose.signinsignup.SignInSignUpViewModel
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.usecase.IsLoginFlowUseCase
import com.sarang.torang.viewmodels.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignInSignUpScreenTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> =
        createAndroidComposeRule<ComponentActivity>()

    @Inject
    lateinit var isLoginFlowUseCase: IsLoginFlowUseCase

    @Inject
    lateinit var loginRepository: LoginRepository

    private lateinit var loginViewModel: SignInSignUpViewModel

    // 리소스 문자열 상수
    private lateinit var loginWithEmailText: String
    private lateinit var dontHaveAccountText: String
    private lateinit var signUpText: String
    private lateinit var lookAroundText: String


    @Before
    fun setScreen() {
        hiltRule.inject()
        loginViewModel = SignInSignUpViewModel(isLoginFlowUseCase)

        // 리소스 문자열 초기화
        loginWithEmailText = composeTestRule.activity.getString(R.string.login_with_email)
        dontHaveAccountText = composeTestRule.activity.getString(R.string.dont_have_an_account)
        signUpText = composeTestRule.activity.getString(R.string.sign_up)
        lookAroundText = composeTestRule.activity.getString(R.string.look_around)

        // 컴포저블 설정
        composeTestRule.setContent {
            SignInSignUpScreen(
                viewModel = loginViewModel,
                showTopBar = true,
                onBack = { },
                onLookAround = { },
                onSignUp = { },
                onSuccessLogin = {}
            )
        }
    }

    @Test
    fun testTextIsDisplayed() = runTest {
        Assert.assertFalse(loginViewModel.isLogin.getOrAwaitValue())
        loginRepository.encEmailLogin("sarang628@naver.com", "aaaaa")
        Assert.assertTrue(loginViewModel.isLogin.getOrAwaitValue())
        // 문구들이 보이지 않는지 확인
        composeTestRule.onNodeWithText(loginWithEmailText).assertIsNotDisplayed()
        composeTestRule.onNodeWithText(dontHaveAccountText).assertIsNotDisplayed()
        composeTestRule.onNodeWithText(signUpText).assertIsNotDisplayed()
        composeTestRule.onNodeWithText(lookAroundText).assertIsNotDisplayed()

        Assert.assertTrue(loginViewModel.isLogin.getOrAwaitValue())
        loginRepository.logout()
        Assert.assertFalse(loginViewModel.isLogin.getOrAwaitValue())

        // 문구들이 보이지 않는지 확인
        composeTestRule.onNodeWithText(loginWithEmailText).assertIsDisplayed()
        composeTestRule.onNodeWithText(dontHaveAccountText).assertIsDisplayed()
        composeTestRule.onNodeWithText(signUpText).assertIsDisplayed()
        composeTestRule.onNodeWithText(lookAroundText).assertIsDisplayed()
    }
}