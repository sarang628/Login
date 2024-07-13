package com.sarang.torang.compose.signinsignup.signup

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.sarang.torang.R
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class SignUpEmailTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    lateinit var whatsYourEmail: String
    lateinit var describeInputEmail: String
    lateinit var labelNext: String

    private var onBack = false
    private var onClear = false
    private var onNext = false
    private var onVerifiedEmail = false
    private var checkedEmailDuplication = false

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SignUpEmail(
                email = "torang@torang.com",
                checkedEmailDuplication = checkedEmailDuplication,
                onValueChange = {},
                onBack = { onBack = true },
                onClear = { onClear = true },
                onNext = { onNext = true },
                onVerifiedEmail = { onVerifiedEmail = true },
                errorMessage = "email duplicated"
            )
        }
        val context = composeTestRule.activity
        whatsYourEmail = context.getString(R.string.what_s_your_email)
        describeInputEmail = context.getString(R.string.describe_input_email)
        labelNext = context.getString(R.string.label_next)
    }

    @Test
    fun testEmailInputScreenDisplaysCorrectly() {
        composeTestRule.onNodeWithText("torang@torang.com").assertIsDisplayed()


        composeTestRule.onNodeWithText(whatsYourEmail)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(describeInputEmail)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(labelNext)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("email duplicated")
            .assertIsDisplayed()
    }

    @Test
    fun callBackTest() {
        assertFalse(onNext)
        composeTestRule.onNodeWithTag("btnNext").performClick()
        assertTrue(onNext)

        assertFalse(onClear)
        composeTestRule.onNodeWithTag("btnClear").performClick()
        assertTrue(onClear)

        assertFalse(onBack)
        composeTestRule.onNodeWithTag("btnBack").performClick()
        assertTrue(onBack)
    }
}