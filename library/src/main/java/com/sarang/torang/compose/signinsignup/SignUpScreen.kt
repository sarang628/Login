package com.sarang.torang.compose.signinsignup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpCodeVerification
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpEmail
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpName
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpPassword
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpSuccess
import com.sarang.torang.compose.signinsignup.signup.SignUpCodeVerification
import com.sarang.torang.compose.signinsignup.signup.SignUpEmail
import com.sarang.torang.compose.signinsignup.signup.SignUpName
import com.sarang.torang.compose.signinsignup.signup.SignUpPassword
import com.sarang.torang.compose.signinsignup.signup.SignUpSuccess
import com.sarang.torang.compose.signinsignup.signup.SignUpUiState
import com.sarang.torang.compose.signinsignup.signup.SignUpViewModel
import com.sarang.torang.screens.login.SignEmail
import com.sarang.torang.screens.login.SignUpConfirmationCode
import com.sarang.torang.screens.login.SignUpName
import com.sarang.torang.screens.login.SignUpPassword
import com.sarang.torang.screens.login.SignUpSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    onBack: () -> Unit,
    signUpSuccess: () -> Unit,
) {
    val navController: NavHostController = rememberNavController()
    // Read screen UI state from the business logic state holder
    val uiState = signUpViewModel.uiState
    val coroutine = rememberCoroutineScope()

    // @formatter:off
    SignUpScreen(
        uiState = uiState,
        signUpSuccess = signUpSuccess,
        navController = navController,
        onBack = { if (!navController.popBackStack()) onBack.invoke() },
        onBackConfirm = signUpViewModel::onBackConfirm ,
        onChangeName = signUpViewModel::onChangeName,
        onClearName = signUpViewModel::clearName,
        onChangeEmail = signUpViewModel::onChangeEmail,
        onClearEmail = signUpViewModel::clearEmail,
        onChangeConfirmationCode = signUpViewModel::onChangeConfirmationCode,
        onClearConfirmationCode = signUpViewModel::clearConfirmationCode,
        onChangePassword = signUpViewModel::onChangePassword,
        onNextEmail =  signUpViewModel::registerEmail ,
        onNextConfirmCode =  signUpViewModel::confirmCode,
        onAlertDismiss =  signUpViewModel::onAlertDismiss ,
        onNextName = { if (signUpViewModel.checkName()) navController.navigate(SignUpPassword) },
        onNextPassword = { if (signUpViewModel.validPassword()) navController.navigate(SignEmail) },
        moveConfirmCode = { navController.navigate(SignUpConfirmationCode) },
        onVerifiedConfirm = { navController.navigate(SignUpSuccess) },
        onMoveBackEmail = {
            signUpViewModel.clearAlertMessage()
            signUpViewModel.onMoveBackEmail()
            coroutine.launch {
                delay(100)
                navController.popBackStack()
            }
        }
    )
    // @formatter:on
}


@Composable
internal fun SignUpScreen(
    uiState: SignUpUiState,
    onBack: () -> Unit,
    onBackConfirm: () -> Unit,
    signUpSuccess: () -> Unit,
    onChangeName: (String) -> Unit,
    onClearName: () -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeConfirmationCode: (String) -> Unit,
    onClearEmail: () -> Unit,
    onClearConfirmationCode: () -> Unit,
    startDestination: Any = SignUpName,
    onNextName: () -> Unit,
    onNextEmail: () -> Unit,
    onNextConfirmCode: () -> Unit,
    onNextPassword: () -> Unit,
    onAlertDismiss: () -> Unit,
    moveConfirmCode: () -> Unit,
    onMoveBackEmail: () -> Unit,
    onVerifiedConfirm: () -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    if (uiState.isProgress) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable<SignUpName> {
                SignUpName(
                    name = uiState.name,
                    errorMessage = uiState.nameErrorMessage,
                    onValueChange = onChangeName,
                    onClear = onClearName,
                    onNext = onNextName,
                    onBack = onBack,
                )
            }
            composable<SignEmail> {
                SignUpEmail(
                    email = uiState.email,
                    checkedEmailDuplication = uiState.checkedEmail,
                    errorMessage = uiState.emailErrorMessage,
                    onValueChange = onChangeEmail,
                    onBack = navController::popBackStack,
                    onClear = onClearEmail,
                    onNext = onNextEmail,
                    onVerifiedEmail = moveConfirmCode
                )
            }
            composable<SignUpConfirmationCode> {
                SignUpCodeVerification(
                    email = uiState.email,
                    confirmCode = uiState.confirmCode,
                    errorMessage = uiState.confirmCodeErrorMessage,
                    checkedConfirm = uiState.checkedConfirm,
                    onValueChange = onChangeConfirmationCode,
                    onClear = onClearConfirmationCode,
                    onNext = onNextConfirmCode,
                    onBack = onBackConfirm,
                    alertMessage = uiState.alertMessage,
                    onAlertDismiss = onAlertDismiss,
                    onMoveBackEmail = onMoveBackEmail,
                    onVerifiedConfirm = onVerifiedConfirm
                )
            }
            composable<SignUpPassword> {
                SignUpPassword(
                    password = uiState.password,
                    onValueChange = onChangePassword,
                    errorMessage = uiState.passwordErrorMessage,
                    onNext = onNextPassword,
                    onBack = onBack
                )
            }
            composable<SignUpSuccess> {
                SignUpSuccess(
                    onNext = signUpSuccess,
                    onBack = onBack
                )
            }
        }
    }
}

@Preview
@Composable
fun SignUpNavHostPreview() {
    val navController: NavHostController = rememberNavController()
    var uiState by remember { mutableStateOf(SignUpUiState()) }
    SignUpScreen(/*Preview*/
        uiState = SignUpUiState(),
        onBack = {
            navController.popBackStack()
        },
        signUpSuccess = {},
        onChangeName = { uiState = uiState.copy(name = it) },
        onClearName = { uiState = uiState.copy(name = "") },
        onChangeEmail = { uiState = uiState.copy(email = it) },
        onChangePassword = { uiState = uiState.copy(password = it) },
        onChangeConfirmationCode = {},
        onClearEmail = {},
        onClearConfirmationCode = {},
        onNextName = { navController.navigate(SignUpPassword) },
        onNextEmail = {
//            uiState = uiState.copy(checkedEmail = true)
            navController.navigate(SignUpConfirmationCode)
        },
        onNextConfirmCode = { navController.navigate(SignUpSuccess) { popUpTo(0) } },
        onNextPassword = { navController.navigate(SignEmail) },
        onAlertDismiss = {},
        navController = navController,
        onBackConfirm = {},
        moveConfirmCode = { navController.navigate(SignUpConfirmationCode) },
        onMoveBackEmail = {},
        onVerifiedConfirm = {}
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
    PreviewSignUpCodeVerification()
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