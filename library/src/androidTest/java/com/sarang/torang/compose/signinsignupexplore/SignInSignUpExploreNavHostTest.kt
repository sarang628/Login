package com.sarang.torang.compose.signinsignupexplore

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.sarang.torang.R
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class SignInSignUpExploreNavHostTest {

//    @get:Rule
//    val composeTestRule = createComposeRule()

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUiState() {
        composeTestRule.setContent {
            SignInSignUpExploreNavHost(
                showTopBar = true,
                onLookAround = {},
                onSignUp = {},
                isLogin = false,
                showLookAround = true
            )
        }
    }

    @Test
    fun showTopBarTest() {
        composeTestRule.onNodeWithContentDescription(
            label = composeTestRule.activity.getString(R.string.a11y_back)
        ).assertIsDisplayed()
    }

    @Test
    fun checkEmailElement() {
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.login_with_email)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.login_with_email)
        ).performClick()
    }

    /*@Test
    fun checkHitTheSpotElement() {
        composeTestRule.onNodeWithText("Hit the spot").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hit the spot").performClick()
    }*/

    @Test
    fun checkSignUpElement() {
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.sign_up)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.sign_up)
        ).performClick()
    }

    @Test
    fun checkLookAroundElement() {
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.look_around)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.look_around)
        ).performClick()
    }
}