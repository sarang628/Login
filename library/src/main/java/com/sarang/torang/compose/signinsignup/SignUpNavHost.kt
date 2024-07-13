package com.sarang.torang.compose.signinsignup

import android.util.Log
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
import com.sarang.torang.compose.signinsignup.signup.SignUpUiState
import com.sarang.torang.compose.signinsignup.signup.SignUpViewModel
import com.sarang.torang.screen.login.JoinEmail
import com.sarang.torang.screen.login.JoinName
import com.sarang.torang.screen.login.SignUpConfirmationCode
import com.sarang.torang.screen.login.SignUpPassword
import com.sarang.torang.screen.login.SuccessSignUp
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

    SignUpScreen(
        uiState = uiState,
        onBack = {
            if (!navController.popBackStack())
                onBack.invoke()
        },
        onBackConfirm = {
            signUpViewModel.onBackConfirm()
        },
        onChangeName = signUpViewModel::onChangeName,
        onClearName = signUpViewModel::clearName,
        onChangeEmail = signUpViewModel::onChangeEmail,
        onClearEmail = signUpViewModel::clearEmail,
        onChangeConfirmationCode = signUpViewModel::onChangeConfirmationCode,
        onClearConfirmationCode = signUpViewModel::clearConfirmationCode,
        onChangePassword = signUpViewModel::onChangePassword,
        onClearPassword = signUpViewModel::clearPassword,
        onNextName = { navController.navigate(SignUpPassword) },
        onNextEmail = { signUpViewModel.registerEmail() },
        onNextConfirmCode = { signUpViewModel.confirmCode() },
        onNextPassword = {
            if (signUpViewModel.validPassword())
                navController.navigate(JoinEmail)
        },
        onAlertDismiss = { signUpViewModel.onAlertDismiss() },
        signUpSuccess = signUpSuccess,
        navController = navController,
        moveConfirmCode = {
            Log.d("__SignUpScreen", "moveConfirmCode")
            navController.navigate(SignUpConfirmationCode)
        },
        onMoveBackEmail = {
            Log.d("__SignUpScreen", "onMoveBackEmail")
            signUpViewModel.clearAlertMessage()
            signUpViewModel.onMoveBackEmail()
            coroutine.launch {
                delay(100)
                navController.popBackStack()
            }
        }
    )
}


@Composable
private fun SignUpScreen(
    uiState: SignUpUiState,
    onBack: () -> Unit,
    onBackConfirm: () -> Unit,
    signUpSuccess: () -> Unit,
    onChangeName: (String) -> Unit,
    onClearName: () -> Unit,
    onClearPassword: () -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeConfirmationCode: (String) -> Unit,
    onClearEmail: () -> Unit,
    onClearConfirmationCode: () -> Unit,
    startDestination: Any = JoinName,
    onNextName: () -> Unit,
    onNextEmail: () -> Unit,
    onNextConfirmCode: () -> Unit,
    onNextPassword: () -> Unit,
    onAlertDismiss: () -> Unit,
    moveConfirmCode: () -> Unit,
    onMoveBackEmail: () -> Unit,
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
            composable<JoinName> {
                SignUpName(
                    name = uiState.name,
                    onValueChange = onChangeName,
                    onClear = onClearName,
                    onNext = onNextName,
                    onBack = onBack,
                )
            }
            composable<JoinEmail> {
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
                SignUpConfirmation(
                    email = uiState.email,
                    confirmCode = uiState.confirmCode,
                    errorMessage = uiState.confirmCodeErrorMessage,
                    onValueChange = onChangeConfirmationCode,
                    onClear = onClearConfirmationCode,
                    onNext = onNextConfirmCode,
                    onBack = onBackConfirm,
                    alertMessage = uiState.alertMessage,
                    onAlertDismiss = onAlertDismiss,
                    onMoveBackEmail = onMoveBackEmail
                )
            }
            composable<SignUpPassword> {
                SignUpPassword(
                    password = uiState.password,
                    onValueChange = onChangePassword,
                    errorMessage = uiState.passwordErrorMessage,
                    onClear = onClearPassword,
                    onNext = onNextPassword,
                    onBack = onBack
                )
            }
            composable<SuccessSignUp> {
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
        onClearPassword = { },
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
        onNextConfirmCode = {
            navController.navigate(SuccessSignUp) { popUpTo(0) }
        },
        onNextPassword = { navController.navigate(JoinEmail) },
        onAlertDismiss = {},
        navController = navController,
        onBackConfirm = {},
        moveConfirmCode = {
            navController.navigate(SignUpConfirmationCode)
        },
        onMoveBackEmail = {}
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