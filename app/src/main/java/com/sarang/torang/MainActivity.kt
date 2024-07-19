package com.sarang.torang

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.compose.signinsignup.LoginNavHost
import com.sarang.torang.compose.signinsignup.LoginNavHostPreview
import com.sarang.torang.compose.signinsignup.SignInSignUpScreenPreview
import com.sarang.torang.compose.signinsignup.SignUpNavHostPreview
import com.sarang.torang.compose.signinsignup.TorangLogo
import com.sarang.torang.compose.signinsignup._SignInSignUpScreen
import com.sarang.torang.compose.signinsignup.signin.PreviewSignInScreen
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpCodeVerification
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpName
import com.sarang.torang.compose.signinsignup.signup.PreviewSignUpPassword
import com.sarang.torang.compose.signinsignup.signup.SignUpName
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import com.sarang.torang.screens.login.SignIn
import com.sarang.torang.screens.login.SignInSignUp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.verticalScroll(state = rememberScrollState())) {
                        Box(modifier = Modifier.size(LocalConfiguration.current.screenHeightDp.dp))
                        {
                            LoginNavHost(onLookAround = {},
                                //showTopBar = false,
                                //showLookAround = true,
                                onBack = {}
                            )
                        }
                        LoginRepositoryTest(loginRepository = loginRepository)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginNavHostPreview1() {
    TorangTheme {
        LoginNavHost(
            onLookAround = { /*TODO*/ },
            signInSignUpScreen = {
                _SignInSignUpScreen(
                    /*Preview*/
                    isLogin = false,
                    showTopBar = false,
                    onBack = { },
                    onLookAround = { },
                    startDestination = SignIn, // SignIn/ChooseLoginMethod
                    torangLogo = { TorangLogo("T O R A N G", "hit the spot") },
                    signInScreen = { PreviewSignInScreen() },
                    onSignUp = { },
                    onEmailLogin = { },
                )
            },
            signUpNavHost = { SignUpNavHostPreview() },
            startDestination = SignInSignUp //SignUp/SignInSignUp
        )
    }
}

@Preview
@Composable
fun PreviewSignUpName1() {
    TorangTheme {
        var name by remember { mutableStateOf("Ludwig") }
        SignUpName(/*Preview*/
            name = name,
            onClear = { name = "" },
            onValueChange = { name = it },
            onNext = {},
            onBack = {},
            errorMessage = null,
            limit = 25
        )
    }
}

@Preview
@Composable
fun PreviewSignUpPassword1() {
    TorangTheme {
        PreviewSignUpPassword()
    }
}

@Preview
@Composable
fun PreviewSignUpCodeVerification() {
    TorangTheme {
        PreviewSignUpCodeVerification()
    }
}
