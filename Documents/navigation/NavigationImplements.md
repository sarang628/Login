# [LoginNavHost](../../library/src/main/java/com/sarang/torang/compose/signinsignup/LoginNavHost.kt)
```
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
```