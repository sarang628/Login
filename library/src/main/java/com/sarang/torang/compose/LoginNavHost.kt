package com.sarang.torang.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.email.EmailLoginScreen
import com.sarang.torang.compose.signinsignupexplore.SignInSignUpExploreNavHost
import com.sarang.torang.compose.signup.SignUpNavHost
import com.sarang.torang.screen.login.SignInSignUpExplore
import com.sarang.torang.screen.login.SignUp
import com.sarang.torang.viewmodels.LoginViewModel

/**
 * 실질적인 로그인 화면의 시작점.
 *
 * SignInSignUpExploreNavHost - 로그인, 회원가입 선택화면
 *
 * SignUpNavHost - 회원 가입 화면
 * @param onSuccessLogin 로그인 성공시 호출되는 콜백
 * @param onLookAround 탐색 화면으로 이동하는 콜백
 * @param showTopBar 상단바를 보여줄지 여부
 * @param onBack 뒤로가기 버튼을 눌렀을 때 호출되는 콜백
 * @param showLookAround 탐색 화면을 보여줄지 여부
 */
object Test

@Composable
fun LoginNavHost(
    viewModel: LoginViewModel = hiltViewModel(),
    onSuccessLogin: () -> Unit,
    onLookAround: () -> Unit,
    showTopBar: Boolean = false,
    onBack: (() -> Unit)? = null,
    showLookAround: Boolean = true,
) {
    val navController = rememberNavController()
    val isLogin by viewModel.isLogin.collectAsState(false)
    NavHost(navController = navController, startDestination = SignInSignUpExplore) {
        composable<SignInSignUpExplore> {
            SignInSignUpExploreNavHost(
                isLogin = isLogin,
                onSignUp = { navController.navigate(SignUp) },
                onLookAround = onLookAround,
                showLookAround = showLookAround,
                showTopBar = showTopBar,
                onBack = onBack,
                loginScreen = {
                    EmailLoginScreen(onLogin = onSuccessLogin)
                }
            )
        }

        composable<SignUp> {
            SignUpNavHost(
                onBack = navController::popBackStack,
                signUpSuccess = navController::popBackStack
            )
        }
    }
}