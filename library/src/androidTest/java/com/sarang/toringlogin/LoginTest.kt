package com.sarang.toringlogin

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.toringlogin.login.compose.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            LoginScreen(
                onLookAround = {},
                onSignUp = {},
                onClickFacebookLogin = {},
                onClickEmail = {}
            )
        }
        composeTestRule.onNodeWithText("LOG IN WITH FACEBOOK").performClick()
        composeTestRule.onNodeWithText("LOG IN WITH EMAIL").performClick()
        composeTestRule.onNodeWithText("Hit the spot").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign up.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Look Around").assertIsDisplayed()

        //Select a single node
//        composeTestRule.onNode(<<SemanticsMatcher>>, useUnmergedTree = false): SemanticsNodeInteraction

        // Example
//        composeTestRule.onNode(hasText("Button")) // Equivalent to onNodeWithText("Button")

        // Select multiple nodes
//        composeTestRule.onAllNodes(<<SemanticsMatcher>>): SemanticsNodeInteractionCollection

        // Example
//        composeTestRule.onAllNodes(hasText("Button")) // Equivalent to onAllNodesWithText("Button")

//        From a test, we can use printToLog() to show the semantics tree:
//        composeTestRule.onRoot().printToLog("TAG")

        // If you need to match a node of what would be the unmerged tree, you can set useUnmergedTree to true:
//        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")

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