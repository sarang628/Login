package com.sarang.torang.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.signinsignup.LoginNavHost
import com.sarang.torang.compose.signinsignup.SignInSignUpScreen
import com.sarang.torang.compose.signinsignup.SignInSignUpViewModel
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.usecase.IsLoginFlowUseCase
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
class LoginNavHostTest {
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


    @Before
    fun setScreen() {
        hiltRule.inject()
        loginViewModel = SignInSignUpViewModel(isLoginFlowUseCase)

        // 컴포저블 설정
        composeTestRule.setContent {
            LoginNavHost(
                showTopBar = true,
                onBack = { },
                onSuccessLogin = { },
                onLookAround = { },
                signInSignUpScreen = {
                    SignInSignUpScreen(
                        viewModel = loginViewModel,
                        showTopBar = true,
                        onBack = { },
                        onSuccessLogin = { },
                        onLookAround = { },
                        onSignUp = { },
                    )
                },
                signUpNavHost = {}
            )
        }
    }

    @Test
    fun testTextIsDisplayed() = runTest {
    }
}