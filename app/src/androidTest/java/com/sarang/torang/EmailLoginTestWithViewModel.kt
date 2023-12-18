package com.sarang.torang

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.email.EmailLoginScreen
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.ValidEmailUseCase
import com.sarang.torang.usecase.ValidPasswordUseCase
import com.sarang.torang.viewmodels.EmailLoginViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class EmailLoginTestWithViewModel {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Inject
    lateinit var emailLoginService: EmailLoginUseCase

    @Inject
    lateinit var emailUseCase: ValidEmailUseCase

    @Inject
    lateinit var passwordUseCase: ValidPasswordUseCase

    lateinit var viewModel: EmailLoginViewModel


    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = EmailLoginViewModel(
            emailUseCase = emailUseCase,
            emailLoginService = emailLoginService,
            passwordUseCase = passwordUseCase
        )
    }

    @Test
    fun testEmailLoginScreen() {
        composeTestRule.setContent {
            EmailLoginScreen(
                viewModel = viewModel,
                onLogin = {

                })
        }
        composeTestRule.onNodeWithText("Log in").performClick()
        composeTestRule.onNodeWithText("이메일 형식이 올바르지 않습니다.").assertIsDisplayed()
    }
}