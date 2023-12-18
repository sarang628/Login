package com.sarang.torang.compose.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.viewmodels.SignUpViewModel
import kotlinx.coroutines.launch


@Composable
fun SignUpNavHost(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    onBack: () -> Unit,
    signUpSuccess: () -> Unit
) {
    val uiState by signUpViewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val coroutine = rememberCoroutineScope()
    if (uiState.isProgress) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        NavHost(navController = navController, startDestination = "JoinName") {
            composable("JoinName") {
                SignUpName(
                    name = uiState.name,
                    onValueChange = { signUpViewModel.onChangeName(it) },
                    onBack = onBack,
                    onClear = { signUpViewModel.clearName() },
                    onNext = { navController.navigate("SignUpPassword") })
            }
            composable("JoinEmail") {
                SignUpEmail(
                    email = uiState.email,
                    errorMessage = uiState.emailErrorMessage,
                    onValueChange = { signUpViewModel.onChangeEmail(it) },
                    onBack = { navController.popBackStack() },
                    onClear = { signUpViewModel.clearEmail() },
                    onNext = {
                        coroutine.launch {
                            if (signUpViewModel.registerEmail()) {
                                navController.navigate("SignUpConfirmationCode")
                            }
                        }
                    }
                )
            }
            composable("SignUpConfirmationCode") {
                SignUpConfirmationScreen(
                    email = uiState.email,
                    confirmCode = uiState.confirmCode,
                    errorMessage = uiState.confirmCodeErrorMessage,
                    onValueChange = { signUpViewModel.onChangeConfirmationCode(it) },
                    onBack = { navController.popBackStack() },
                    onClear = { signUpViewModel.clearConfirmationCode() },
                    onNext = {
                        coroutine.launch {
                            if (signUpViewModel.confirmCode()) {
                                navController.navigate("SuccessSignUp") {
                                    popUpTo(0)
                                }
                            }
                        }
                    })
            }
            composable("SignUpPassword") {
                SignUpPassword(
                    password = uiState.password,
                    onValueChange = { signUpViewModel.onChangePassword(it) },
                    onBack = { navController.popBackStack() },
                    onClear = { signUpViewModel.clearPassword() },
                    onNext = {
                        coroutine.launch {
                            if (signUpViewModel.validPassword())
                                navController.navigate("JoinEmail")
                        }
                    },
                    errorMessage = uiState.passwordErrorMessage
                )
            }
            composable("SuccessSignUp") {
                SignUpSuccess(onNext = signUpSuccess)
            }
        }
    }
}