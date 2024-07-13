package com.sarang.torang.compose.signinsignup.signup

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signup.SignUpEmail

@RunWith(AndroidJUnit4::class)
class SignUpEmailTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUi() {
        composeTestRule.setContent {
            SignUpEmail(
                email = "torang@torang.com",
                onValueChange = {},
                onBack = { /*TODO*/ },
                onClear = { /*TODO*/ },
                onNext = {},
                checkedEmail = false,
                moveConfirmCode = {}
            )
        }
    }

    @Test
    fun staticComponentTest() {
        composeTestRule.onNodeWithText("torang@torang.com").assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.what_s_your_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.describe_input_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.label_next))
            .assertIsDisplayed()
    }
}