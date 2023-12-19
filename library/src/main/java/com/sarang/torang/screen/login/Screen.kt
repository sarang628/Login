package com.sarang.torang.screen.login

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    object SignInSignUpExplore : Screen("signInSignUpExplore")
    object ChooseLoginMethod : Screen("chooseLoginMethod")
    object SignUp : Screen("signUp")
    object JoinName : Screen("joinName")
    object JoinEmail : Screen("joinEmail")
    object SignUpConfirmationCode : Screen("signUpConfirmationCode")
    object SignUpPassword : Screen("signUpPassword")
    object SuccessSignUp : Screen("successSignUp")
    object EmailLogin : Screen("emailLogin")
}