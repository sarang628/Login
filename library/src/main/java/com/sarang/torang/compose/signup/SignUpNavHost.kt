package com.sarang.torang.compose.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.R
import com.sarang.torang.screen.login.Screen
import com.sarang.torang.viewmodels.SignUpViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        if (navController.currentDestination?.route != Screen.SuccessSignUp.route)
                            IconButton(onClick = {
                                if (!navController.popBackStack())
                                    onBack.invoke()
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = stringResource(id = R.string.a11y_back)
                                )
                            }
                    }
                )
            },
        ) {
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = Screen.JoinName.route
            ) {
                composable(Screen.JoinName.route) {
                    SignUpName(
                        name = uiState.name,
                        onValueChange = signUpViewModel::onChangeName,
                        onBack = onBack,
                        onClear = signUpViewModel::clearName,
                        onNext = { navController.navigate(Screen.SignUpPassword.route) })
                }
                composable(Screen.JoinEmail.route) {
                    SignUpEmail(
                        email = uiState.email,
                        errorMessage = uiState.emailErrorMessage,
                        onValueChange = signUpViewModel::onChangeEmail,
                        onBack = navController::popBackStack,
                        onClear = signUpViewModel::clearEmail,
                        onNext = {
                            coroutine.launch {
                                if (signUpViewModel.registerEmail()) {
                                    navController.navigate(Screen.SignUpConfirmationCode.route)
                                }
                            }
                        }
                    )
                }
                composable(Screen.SignUpConfirmationCode.route) {
                    SignUpConfirmationScreen(
                        email = uiState.email,
                        confirmCode = uiState.confirmCode,
                        errorMessage = uiState.confirmCodeErrorMessage,
                        onValueChange = signUpViewModel::onChangeConfirmationCode,
                        onBack = navController::popBackStack,
                        onClear = signUpViewModel::clearConfirmationCode,
                        onNext = {
                            coroutine.launch {
                                if (signUpViewModel.confirmCode()) {
                                    navController.navigate(Screen.SuccessSignUp.route) {
                                        popUpTo(0)
                                    }
                                }
                            }
                        })
                }
                composable(Screen.SignUpPassword.route) {
                    SignUpPassword(
                        password = uiState.password,
                        onValueChange = signUpViewModel::onChangePassword,
                        errorMessage = uiState.passwordErrorMessage,
                        onBack = navController::popBackStack,
                        onClear = signUpViewModel::clearPassword,
                        onNext = {
                            if (signUpViewModel.validPassword())
                                navController.navigate(Screen.JoinEmail.route)
                        }
                    )
                }
                composable(Screen.SuccessSignUp.route) {
                    SignUpSuccess(onNext = signUpSuccess)
                }
            }
        }
    }
}