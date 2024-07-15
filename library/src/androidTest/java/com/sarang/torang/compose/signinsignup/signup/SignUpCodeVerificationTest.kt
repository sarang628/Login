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
class SignUpCodeVerificationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var enterTheConfirmationCode: String
    private lateinit var enterTheConfirmationCodeDesc: String
    private lateinit var labelNext: String
    private lateinit var confirm: String

    private var onBack = false
    private var onClear = false
    private var onNext = false
    private var onVerifiedEmail = false

    @Before
    fun setUp() {
        val context = composeTestRule.activity
        enterTheConfirmationCode = context.getString(R.string.enter_the_confirmation_code)
        enterTheConfirmationCodeDesc =
            context.getString(R.string.enter_the_confirmation_code_desc)
        labelNext = context.getString(R.string.label_next)
        confirm = context.getString(R.string.confirm)
    }

    private fun uiSetup() {
        composeTestRule.setContent {
            SignUpCodeVerification(
                email = "torang@torang.com",
                onValueChange = {},
                onBack = { onBack = true },
                onClear = { onClear = true },
                onNext = { onNext = true },
                errorMessage = "email duplicated",
                onAlertDismiss = {},
                onMoveBackEmail = {},
                onVerifiedConfirm = { onVerifiedEmail = true },
                checkedConfirm = false,
                confirmCode = "123456"
            )
        }
    }

    @Test
    fun testEmailInputScreenDisplaysCorrectly() {
        uiSetup()
        composeTestRule.onNodeWithText(enterTheConfirmationCode)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(
            String.format(
                enterTheConfirmationCodeDesc,
                "torang@torang.com"
            )
        )
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(labelNext)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("email duplicated")
            .assertIsDisplayed()
    }

    @Test
    fun callBackTest() {
        uiSetup()
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

    @Test
    fun dialogDisplaysCorrectlyTest() {
        // Set the content to show the dialog
        composeTestRule.setContent {
            SignUpCodeVerification(
                email = "torang@torang.com",
                onValueChange = {},
                onBack = { onBack = true },
                onClear = { onClear = true },
                onNext = { onNext = true },
                errorMessage = "email duplicated",
                onAlertDismiss = {},
                onMoveBackEmail = {},
                onVerifiedConfirm = { onVerifiedEmail = true },
                checkedConfirm = false,
                confirmCode = "123456",
                alertMessage = "Dialog message here" // Ensure this is the condition to show dialog
            )
        }

        // Verify the dialog is displayed
        composeTestRule.onNodeWithText("Dialog message here").assertIsDisplayed()

        // Perform actions or checks on dialog buttons if any
        composeTestRule.onNodeWithText(confirm).performClick()
    }
}