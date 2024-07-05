package com.sarang.torang.compose.signinsignup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.sarang.torang.compose.signinsignup.signin.SignInScreen
import com.sarang.torang.compose.signinsignup.signinsignup.SignInSignUpNavHost
import com.sarang.torang.compose.signinsignup.signup.SignUpNavHost
import com.sarang.torang.screen.login.SignInSignUp
import com.sarang.torang.screen.login.SignUp

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
    viewModel: LoginViewModel = hiltViewModel(),
    onSuccessLogin: () -> Unit,
    onLookAround: () -> Unit,
    showTopBar: Boolean = false,
    onBack: (() -> Unit)? = null,
    showLookAround: Boolean = true,
    navController: NavHostController = rememberNavController(),
) {
    val isLogin by viewModel.isLogin.collectAsState(false)

    val graph: NavGraph = remember(navController) {
        navController.createGraph(SignInSignUp, null, emptyMap()) {
            composable<SignInSignUp> {
                SignInSignUpNavHost(
                    isLogin = isLogin,
                    onSignUp = { navController.navigate(SignUp) },
                    onLookAround = onLookAround,
                    showLookAround = showLookAround,
                    showTopBar = showTopBar,
                    onBack = onBack,
                    loginScreen = {
                        SignInScreen(onLogin = onSuccessLogin)
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

    NavHost(navController = navController, graph = graph)
}