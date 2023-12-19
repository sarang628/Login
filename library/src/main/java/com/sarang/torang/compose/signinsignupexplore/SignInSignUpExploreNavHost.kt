package com.sarang.torang.compose.signinsignupexplore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.R
import com.sarang.torang.compose.TorangLogo
import com.sarang.torang.screen.login.Screen

@Composable
internal fun SignInSignUpExploreNavHost(
    isLogin: Boolean,
    onSignUp: () -> Unit,               // 회원가입 클릭
    onLookAround: () -> Unit,           // 둘러보기 클릭
    goEmailLoginDirect: Boolean = false,
    showLookAround: Boolean = true,
    showTopBar: Boolean = false,
    onBack: (() -> Unit)? = null,
    loginScreen: @Composable (() -> Unit)? = null
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            if (showTopBar)
                SignInSignUpExploreTopAppBar(
                    onBack = {
                        if (!navController.popBackStack()) {
                            onBack?.invoke()
                        }
                    }
                )
        }
    ) {
        Column(
            Modifier
                .height(LocalConfiguration.current.screenHeightDp.dp)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .verticalScroll(state = rememberScrollState())
        ) {
            TorangLogo()
            if (!isLogin) {
                NavHost(
                    navController = navController,
                    startDestination = if (goEmailLoginDirect) Screen.EmailLogin.route else Screen.ChooseLoginMethod.route
                ) {
                    composable(Screen.ChooseLoginMethod.route) {
                        SignInSignUpExplore(
                            onEmailLogin = {
                                navController.navigate(Screen.EmailLogin.route)
                            }, onSignUp = onSignUp,
                            onLookAround = onLookAround,
                            showLookAround = showLookAround
                        )
                    }
                    composable(Screen.EmailLogin.route) {
                        loginScreen?.invoke()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInSignUpExploreTopAppBar(
    onBack: (() -> Unit)? = null
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = {
                onBack?.invoke()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    stringResource(id = R.string.a11y_back)
                )
            }
        })
}

@Composable
fun SignInSignUpExplore(
    onEmailLogin: () -> Unit,
    onSignUp: () -> Unit,               // 회원가입 클릭
    onLookAround: () -> Unit,           // 둘러보기 클릭
    showLookAround: Boolean = true
) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        /*email 로그인 버튼*/
        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { onEmailLogin.invoke() }) {
            Text(text = stringResource(id = R.string.login_with_email))
        }
        /*sign up*/
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.dont_have_an_account))
            TextButton(onClick = { onSignUp.invoke() }) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        /*Look Around*/
        if (showLookAround) {
            TextButton(onClick = { onLookAround.invoke() }) {
                Text(
                    text = stringResource(id = R.string.look_around),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewChooseLoginMethod() {
    SignInSignUpExplore(
        onEmailLogin = { /*TODO*/ },
        onSignUp = { /*TODO*/ },
        onLookAround = { /*TODO*/ },
        showLookAround = true
    )
}

@Preview
@Composable
fun PreviewSignInSignUpExploreNavHost() {
    SignInSignUpExploreNavHost(
        isLogin = false,
        goEmailLoginDirect = false,
        onSignUp = {},
        onLookAround = {},
    )
}