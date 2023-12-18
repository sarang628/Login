package com.sryang.torang

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescriptionExactly
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sryang.torang.compose.LoginScreen
import com.sryang.torang.compose.email.EmailLoginScreen
import com.sryang.torang.uistate.EmailLoginUiState
import com.sryang.torang.uistate.LoginUiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailLoginTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Before
    fun setUiState() {
        composeTestRule.setContent {
            EmailLoginScreen(
                uiState = EmailLoginUiState(
                    passwordErrorMessage = "password error"
                ),
                onLogin = { id, password -> {} },
                onChangeEmail = {},
                onChangePassword = {},
                onClearEmail = {},
                onClearErrorMsg = {},
                onClearPassword = {}
            )
        }
    }

    @Test
    fun checkLoginButton() {
        composeTestRule.onNodeWithText("Log in").assertIsDisplayed()
        composeTestRule.onNodeWithText("Log in").performClick()
    }

    @Test
    fun checkErrorPassword() {
        composeTestRule.onNode(hasText("password error")).assertIsDisplayed()
    }

    @Test
    fun test1(){
        // If you need to match a node of what would be the unmerged tree, you can set useUnmergedTree to true:
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")
    }

    @Test
    fun myTest() {
        //composeTestRule.onNodeWithText("LOG IN WITH FACEBOOK").performClick()

        // Example
        //composeTestRule.onNode(!hasText("Button1")) // Equivalent to onNodeWithText("Button")
        // Example
        //composeTestRule.onAllNodes(hasText("Button")) // Equivalent to onAllNodesWithText("Button")


//        Assertions
//        Check assertions by calling assert() on the SemanticsNodeInteraction returned by a finder with one or multiple matchers:
        // Single matcher:
        //composeTestRule.onNode(matcher).assert(hasText("Button")) // hasText is a SemanticsMatcher

// Multiple matchers can use and / or
        //composeTestRule.onNode(matcher).assert(hasText("Button") or hasText("Button2"))

        // Check number of matched nodes
        //composeTestRule.onAllNodesWithContentDescription("Beatle").assertCountEquals(4)
        // At least one matches
        //composeTestRule.onAllNodesWithContentDescription("Beatle").assertAny(hasTestTag("Drummer"))
        // All of them match
        //composeTestRule.onAllNodesWithContentDescription("Beatle").assertAll(hasClickAction())

    }

    // 로그인 중일 떄는 이메일이나 패스워드가 수정이 불가능 해야 한다.
}