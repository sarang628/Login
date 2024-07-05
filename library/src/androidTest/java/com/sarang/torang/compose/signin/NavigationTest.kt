package com.sarang.torang.compose.signin

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signinsignup.SignInSignUpNavHost
import com.sarang.torang.screen.login.EmailLogin
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SignInSignUpNavHost(
                navController = navController,
                onLookAround = {},
                isLogin = false,
                onSignUp = {})
        }
    }

    // Unit test
    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.login_with_email))
            .assertIsDisplayed()
    }

    @Test
    fun appNavHost_clickLoginWithEmail_navigateToLoginWithEmail() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.login_with_email))
            .performScrollTo()
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, EmailLogin.toString().split("@")[0])
    }
}