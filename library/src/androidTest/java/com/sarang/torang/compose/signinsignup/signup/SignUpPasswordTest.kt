package com.sarang.torang.compose.signinsignup.signup

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signup.SignUpPassword
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class SignUpPasswordTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private var onback = false
    private var onNext = false
    private var onValueChange = false

    @Before
    fun initUI() {
        composeTestRule.setContent {
            SignUpPassword(
                password = "abcde",
                onValueChange = { onValueChange = true },
                onNext = { onNext = true },
                onBack = { onback = true }
            )
        }
    }

    @Test
    fun testStaticElement() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.create_a_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.describe_input_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.label_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.label_next))
            .assertIsDisplayed()
    }

    @Test
    fun checkNextButton() {
        assertFalse(onback)
        composeTestRule.onNodeWithTag("btnBack").performClick()
        assertTrue(onback)

        assertFalse(onNext)
        composeTestRule.onNodeWithTag("btnNext").performClick()
        assertTrue(onNext)

        assertFalse(onValueChange)
        composeTestRule.onNodeWithTag("tfPassword").performTextInput("abc")
        assertTrue(onValueChange)
    }
}