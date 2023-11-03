package com.sarang.toringlogin.login.compose.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.toringlogin.login.compose.email.LoginOutlinedTextField
import com.sarang.toringlogin.login.viewmodels.SignUpViewModel
import kotlinx.coroutines.launch


@Composable
fun SignUpScreen(
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
                JoinName(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinName(
    name: String,
    onValueChange: (String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
    onNext: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(

                ),
                title = {
                    Text("")
                },
                navigationIcon = {
                    IconButton(onClick = { onBack.invoke() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(text = "What's your name?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            LoginOutlinedTextField(
                label = "Full name",
                value = name,
                onValueChange = onValueChange,
                placeHolder = "Full name",
                onClear = onClear
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(modifier = Modifier.fillMaxWidth(), onClick = { onNext.invoke() }) {
                Text(text = "Next")
            }
        }
    }
}

@Preview
@Composable
fun PreviewJoinName() {
    JoinName(
        name = "",
        onClear = {},
        onValueChange = {},
        onBack = {},
        onNext = {}
    )
}