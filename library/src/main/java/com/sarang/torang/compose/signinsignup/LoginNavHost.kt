package com.sarang.torang.compose.signinsignup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.sarang.torang.screens.login.SignInSignUp
import com.sarang.torang.screens.login.SignUp

/**
 * 로그인 화면 시작점.
 * Entry point for a SignIn/Up screen.
 *
 * 로그인/회원가입 선택화면과 회원가입 화면으로 나누어짐.
 *
 * SignInSignUpExploreNavHost - 로그인, 회원가입 선택화면
 *
 * SignUpNavHost - 회원 가입 화면
 *
 * @param onSuccessLogin 로그인 성공시 호출되는 콜백
 * @param onLookAround 탐색 화면으로 이동하는 콜백
 * @param showTopBar 상단바를 보여줄지 여부
 * @param onBack 뒤로가기 버튼을 눌렀을 때 호출되는 콜백
 * @param showLookAround 탐색 화면을 보여줄지 여부
 */
@Composable
fun LoginNavHost(
    onLookAround: () -> Unit,
    showTopBar: Boolean = false,
    onBack: (() -> Unit)? = null,
    showLookAround: Boolean = true,
    startDestination: Any = SignInSignUp,
    navController: NavHostController = rememberNavController(),
    signInSignUpScreen: @Composable () -> Unit = {
        SignInSignUpScreen(
            showTopBar = showTopBar,
            onBack = { onBack?.invoke() },
            onLookAround = onLookAround,
            showLookAround = showLookAround,
            onSignUp = { navController.navigate(SignUp) }
        )
    },
    signUpNavHost: @Composable () -> Unit = {
        SignUpScreen(
            onBack = navController::popBackStack,
            signUpSuccess = navController::popBackStack
        )
    },
) {
    val graph: NavGraph = remember(navController) {
        navController.createGraph(startDestination, null, emptyMap()) {
            composable<SignInSignUp> { signInSignUpScreen.invoke() }
            composable<SignUp> { signUpNavHost.invoke() }
        }
    }

    NavHost(navController = navController, graph = graph)
}

@Preview
@Composable
fun LoginNavHostPreview() {
    LoginNavHost(
        onLookAround = { /*TODO*/ },
        signInSignUpScreen = { SignInSignUpScreenPreview() },
        signUpNavHost = { SignUpNavHostPreview() },
        startDestination = SignInSignUp //SignUp/SignInSignUp
    )
}