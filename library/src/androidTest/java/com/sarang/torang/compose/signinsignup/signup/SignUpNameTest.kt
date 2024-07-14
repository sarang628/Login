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
import com.sarang.torang.compose.signinsignup.signup.SignUpName
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class SignUpNameTest {
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private var onback = false
    private var onNext = false
    private var onClear = false
    private var onValueChange = false

    @Before
    fun setUI() {
        composeTestRule.setContent {
            SignUpName(
                name = "MyName",
                errorMessage = R.string.invalid_email_format,
                onValueChange = { onValueChange = true },
                onBack = { onback = true },
                onClear = { onClear = true },
                onNext = { onNext = true }
            )
        }
    }

    @Test
    fun checkStaticElement() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.label_full_name))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.what_s_your_name))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.label_next))
            .assertIsDisplayed()
    }

    @Test
    fun checkInputName() {
        composeTestRule.onNodeWithText("MyName").assertIsDisplayed()
    }

    @Test
    fun displayerrorMessage() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.invalid_email_format))
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

        assertFalse(onClear)
        composeTestRule.onNodeWithTag("btnClear").performClick()
        assertTrue(onClear)

        assertFalse(onValueChange)
        composeTestRule.onNodeWithTag("tfName").performTextInput("abc")
        assertTrue(onValueChange)
    }
}