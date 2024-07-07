package com.sarang.torang.compose.signinsignup

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpConfirmation
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpEmail
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpName
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpPassword
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpSuccess
import com.sarang.torang.compose.signinsignup.signup.SignUpConfirmation
import com.sarang.torang.compose.signinsignup.signup.SignUpEmail
import com.sarang.torang.compose.signinsignup.signup.SignUpName
import com.sarang.torang.compose.signinsignup.signup.SignUpPassword
import com.sarang.torang.compose.signinsignup.signup.SignUpSuccess
import com.sarang.torang.compose.signinsignup.signup.SignUpViewModel
import com.sarang.torang.screen.login.JoinEmail
import com.sarang.torang.screen.login.JoinName
import com.sarang.torang.screen.login.SignUpConfirmationCode
import com.sarang.torang.screen.login.SignUpPassword
import com.sarang.torang.screen.login.SuccessSignUp
import kotlinx.coroutines.launch

/**
 * 회원 가입 화면
 * @param signUpViewModel 회원 가입 뷰모델
 * @param onBack 뒤로가기
 * @param signUpSuccess 회원 가입 성공
 */
@Composable
fun SignUpNavHost(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    onBack: () -> Unit,
    signUpSuccess: () -> Unit,
) {
    val uiState = signUpViewModel.uiState
    SignUpNavHost(
        name = uiState.name,
        email = uiState.email,
        password = uiState.password,
        confirmCode = uiState.confirmCode,
        onBack = onBack,
        signUpSuccess = signUpSuccess,
        isProgress = uiState.isProgress,
        onChangeName = signUpViewModel::onChangeName,
        onClearName = signUpViewModel::clearName,
        errorMessage = uiState.emailErrorMessage,
        onChangeEmail = signUpViewModel::onChangeEmail,
        onClearEmail = signUpViewModel::clearEmail,
        confirmCodeErrorMessage = uiState.confirmCodeErrorMessage,
        onChangeConfirmationCode = signUpViewModel::onChangeConfirmationCode,
        onClearConfirmationCode = signUpViewModel::clearConfirmationCode,
        onConfirmCode = { signUpViewModel.confirmCode() },
        onChangePassword = signUpViewModel::onChangePassword,
        passwordErrorMessage = uiState.passwordErrorMessage,
        onClearPassword = signUpViewModel::clearPassword,
        validPassword = { signUpViewModel.validPassword() },
        registerEmail = { signUpViewModel.registerEmail() }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpNavHost(
    name: String,
    email: String,
    password: String,
    confirmCode: String,
    onBack: () -> Unit,
    signUpSuccess: () -> Unit,
    isProgress: Boolean,
    onChangeName: (String) -> Unit,
    onClearName: () -> Unit,
    onClearPassword: () -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onConfirmCode: suspend () -> Boolean,
    registerEmail: suspend () -> Boolean,
    validPassword: () -> Boolean,
    onChangeConfirmationCode: (String) -> Unit,
    onClearEmail: () -> Unit,
    onClearConfirmationCode: () -> Unit,
    errorMessage: String?,
    confirmCodeErrorMessage: String?,
    passwordErrorMessage: String?,
) {
    val navController = rememberNavController()
    val coroutine = rememberCoroutineScope()
    if (isProgress) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        if (navController.currentDestination?.route != SuccessSignUp.toString())
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
                startDestination = JoinName
            ) {
                composable<JoinName> {
                    SignUpName(
                        name = name,
                        onValueChange = onChangeName,
                        onBack = onBack,
                        onClear = onClearName,
                        onNext = { navController.navigate(SignUpPassword) })
                }
                composable<JoinEmail> {
                    SignUpEmail(
                        email = email,
                        errorMessage = errorMessage,
                        onValueChange = onChangeEmail,
                        onBack = navController::popBackStack,
                        onClear = onClearEmail,
                        onNext = {
                            coroutine.launch {
                                if (registerEmail()) {
                                    navController.navigate(SignUpConfirmationCode)
                                }
                            }
                        }
                    )
                }
                composable<SignUpConfirmationCode> {
                    SignUpConfirmation(
                        email = email,
                        confirmCode = confirmCode,
                        errorMessage = confirmCodeErrorMessage,
                        onValueChange = onChangeConfirmationCode,
                        onClear = onClearConfirmationCode,
                        onNext = {
                            coroutine.launch {
                                if (onConfirmCode.invoke()) {
                                    navController.navigate(SuccessSignUp) {
                                        popUpTo(0)
                                    }
                                }
                            }
                        })
                }
                composable<SignUpPassword> {
                    SignUpPassword(
                        password = password,
                        onValueChange = onChangePassword,
                        errorMessage = passwordErrorMessage,
                        onClear = onClearPassword,
                        onNext = {
                            if (validPassword())
                                navController.navigate(JoinEmail)
                        }
                    )
                }
                composable<SuccessSignUp> {
                    SignUpSuccess(onNext = signUpSuccess)
                }
            }
        }
    }
}

@Preview
@Composable
fun SignUpNavHostPreview() {
    var name by remember { mutableStateOf("") }
    SignUpNavHost(
        name = name,
        email = "",
        password = "",
        confirmCode = "",
        onBack = {},
        signUpSuccess = {},
        isProgress = false,
        onChangeName = { name = it },
        onClearName = { name = "" },
        onClearPassword = {},
        onChangeEmail = {},
        onChangePassword = {},
        onConfirmCode = { true },
        registerEmail = { true },
        validPassword = { true },
        onChangeConfirmationCode = {},
        onClearEmail = {},
        onClearConfirmationCode = {},
        errorMessage = null,
        confirmCodeErrorMessage = null,
        passwordErrorMessage = null
    )
}


@Preview
@Composable
fun SignUpNamePreview() {
    PreviewSignUpName()
}

@Preview
@Composable
fun SignUpEmailPreview() {
    PreviewSignUpEmail()
}

@Preview
@Composable
fun SignUpConfirmationScreenPreview() {
    PreviewSignUpConfirmation()
}

@Preview
@Composable
fun SignUpPasswordPreview() {
    PreviewSignUpPassword()
}

@Preview
@Composable
fun SignUpSuccessPreview() {
    PreviewSignUpSuccess()
}