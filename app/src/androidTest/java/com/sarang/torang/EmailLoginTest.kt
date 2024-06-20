package com.sarang.torang

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.email.EmailLoginScreen
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.ValidEmailUseCase
import com.sarang.torang.usecase.ValidPasswordUseCase
import com.sarang.torang.viewmodels.EmailLoginViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class EmailLoginTest {

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

    private lateinit var viewModel: EmailLoginViewModel

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = EmailLoginViewModel(
            emailUseCase = emailUseCase,
            emailLoginService = emailLoginService,
            passwordUseCase = passwordUseCase
        )

        composeTestRule.setContent {
            EmailLoginScreen(
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
        delay(10000)
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
    }
}