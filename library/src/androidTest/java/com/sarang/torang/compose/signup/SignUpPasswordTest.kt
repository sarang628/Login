package com.sarang.torang.compose.signup

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
import com.sarang.torang.compose.signinsignup.signup.SignUpPassword

@RunWith(AndroidJUnit4::class)
class SignUpPasswordTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun initUI() {
        composeTestRule.setContent {
            SignUpPassword(
                password = "abcde",
                onValueChange = {},
                onBack = { /*TODO*/ },
                onClear = { /*TODO*/ },
                onNext = {})
        }
    }

    @Test
    fun testStaticElement() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.create_a_password)).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.describe_input_password)).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.label_password)).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.label_next)).assertIsDisplayed()
    }

}