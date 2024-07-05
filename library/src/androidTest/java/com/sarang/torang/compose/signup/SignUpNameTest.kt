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
import com.sarang.torang.compose.signinsignup.signup.SignUpName

@RunWith(AndroidJUnit4::class)
class SignUpNameTest {
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUI() {
        composeTestRule.setContent {
            SignUpName(
                name = "MyName",
                onValueChange = {},
                onBack = { /*TODO*/ },
                onClear = { /*TODO*/ },
                onNext = {}
            )
        }
    }

    @Test
    fun checkStaticElement() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.label_full_name)).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.what_s_your_name)).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.label_next)).assertIsDisplayed()
    }

    @Test
    fun checkInputName() {
        composeTestRule.onNodeWithText("MyName").assertIsDisplayed()
    }
}