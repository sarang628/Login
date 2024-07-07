package com.sarang.torang

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.signinsignup.signin.SignInScreen
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.ValidEmailUseCase
import com.sarang.torang.usecase.ValidPasswordUseCase
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
class SignInTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Inject
    lateinit var emailLoginService: EmailLoginUseCase

    @Inject
    lateinit var emailUseCase: ValidEmailUseCase

    @Inject
    lateinit var passwordUseCase: ValidPasswordUseCase

    private lateinit var viewModel: SignInViewModel

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = SignInViewModel(
            emailUseCase = emailUseCase,
            emailLoginService = emailLoginService,
            passwordUseCase = passwordUseCase
        )

        composeTestRule.setContent {
            SignInScreen(
                viewModel = viewModel,
                onLogin = {}
            )
        }
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
}