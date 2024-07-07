package com.sarang.torang.compose.signinsignup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signin.PreviewSignInScreen
import com.sarang.torang.compose.signinsignup.signin.SignInScreen
import com.sarang.torang.screen.login.ChooseLoginMethod
import com.sarang.torang.screen.login.SignIn
import com.sarang.torang.screen.login.SignUp
import kotlinx.coroutines.delay


@Composable
internal fun SignInSignUpScreen(
    isLogin: Boolean,
    showTopBar: Boolean,
    onBack: (() -> Unit),
    onSuccessLogin: () -> Unit,
    onLookAround: () -> Unit,
    showLookAround: Boolean = true,
    onSignUp: () -> Unit,
    startDestination: Any = ChooseLoginMethod,
    signInScreen: @Composable () -> Unit = { SignInScreen(onLogin = onSuccessLogin) },
    torangLogo: @Composable (() -> Unit) = { TorangLogo() },
    navController: NavHostController = rememberNavController(),
) {
    var currentScreen: String? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = "") {
        navController.currentBackStackEntryFlow.collect {
            currentScreen = it.destination.route
        }
    }

    TorangLogoScaffold( //Custom Scaffold
        isLogin = isLogin,
        showTopBar = showTopBar || currentScreen?.isEmailLoginScreen() == true,
        onBack = {
            if (!navController.popBackStack())
                onBack.invoke()
        },
        torangLogo = torangLogo,
    ) {
        NavHost( //Navigation
            navController = navController,
            startDestination = startDestination
        ) {
            composable<ChooseLoginMethod> {
                SignInSignUpComponent(
                    onEmailLogin = {
                        navController.navigate(SignIn)
                    }, onSignUp = onSignUp,
                    onLookAround = onLookAround,
                    showLookAround = showLookAround
                )
            }
            composable<SignIn> {
                signInScreen.invoke()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TorangLogoScaffold(
    isLogin: Boolean,
    showTopBar: Boolean = false,
    onBack: (() -> Unit)? = null,
    torangLogo: @Composable (() -> Unit) = { TorangLogo() },
    contents: @Composable (() -> Unit),
) {
    Scaffold(
        topBar = {
            if (showTopBar)
                SignInSignUpTopAppBar(
                    onBack = { onBack?.invoke() }
                )
            else {
                TopAppBar(title = {})
            }
        },
        contentWindowInsets = WindowInsets(left = 12.dp, right = 12.dp)
    ) {
        Column(
            Modifier
                .height(LocalConfiguration.current.screenHeightDp.dp)
                .fillMaxWidth()
                .padding(it)
                .verticalScroll(state = rememberScrollState())
        ) {
            torangLogo.invoke()
            if (!isLogin) {
                contents.invoke()
            }
        }
    }
}

private fun String.isEmailLoginScreen(): Boolean {
    return SignIn.toString().split("@")[0] == this
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInSignUpTopAppBar(
    onBack: (() -> Unit)? = null,
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

/**
 * 로그인 회원가입 선택화면.
 *
 * @param onEmailLogin 로그인 클릭
 * @param onSignUp 회원가입 클릭
 * @param onLookAround 둘러보기 클릭
 * @param showLookAround 둘러보기 버튼 표시 여부
 */
@Composable
fun SignInSignUpComponent(
    onEmailLogin: () -> Unit,
    onSignUp: () -> Unit,               // 회원가입 클릭
    onLookAround: () -> Unit,           // 둘러보기 클릭
    showLookAround: Boolean = true,
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

@Composable
fun TorangLogo(previewTitle: String = "", previewSubtitle: String = "") {
    var title by remember { mutableStateOf(previewTitle) }
    var subtitle by remember { mutableStateOf(previewSubtitle) }
    val delay = 50L

    val titleText = "T O R A N G"
    val subtitleText = "hit the spot"

    LaunchedEffect(Unit) {
        val titleBuilder = StringBuilder()
        val subtitleBuilder = StringBuilder()

        // Title animation
        for (char in titleText) {
            delay(delay)
            titleBuilder.append(char)
            title = "$titleBuilder" + "_"
        }
        title = titleBuilder.toString() // Finalize title without cursor

        // Subtitle animation
        for (char in subtitleText) {
            delay(delay)
            subtitleBuilder.append(char)
            subtitle = "$subtitleBuilder" + "_"
        }
        subtitle = subtitleBuilder.toString() // Finalize subtitle without cursor
    }

    Column {
        Spacer(modifier = Modifier.height(100.dp))
        Column(Modifier.fillMaxWidth())
        {
            /*T O R A N G 제목*/
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = title, fontSize = 45.sp, fontWeight = FontWeight.Bold)
            }
            /*hit the spot 부제*/
            Spacer(modifier = Modifier.height(30.dp))
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = subtitle, fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.height(130.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun TorangLogoPreview() {
    TorangLogo("T O R A N G", "hit the spot")
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SignInSignUpComponentPreview() {
    SignInSignUpComponent(/*Preview*/
        onEmailLogin = { /*TODO*/ },
        onSignUp = { /*TODO*/ },
        onLookAround = { /*TODO*/ },
        showLookAround = true
    )
}


@Preview
@Composable
fun TorangLogoScaffoldPreview() {
    TorangLogoScaffold(/*Preview*/
        isLogin = false,
        showTopBar = true,
        torangLogo = { TorangLogo("T O R A N G", "hit the spot") }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(440.dp)
                .background(Color(0xFFEEEEEE))
        ) {
            Text(text = "contents", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview
@Composable
fun SignInSignUpTopAppBarPreview() {
    SignInSignUpTopAppBar()
}

@Preview
@Composable
fun SignInSignUpScreenPreview() {
    SignInSignUpScreen(/*Preview*/
        isLogin = false,
        showTopBar = false,
        onBack = { /*TODO*/ },
        onSuccessLogin = { /*TODO*/ },
        onLookAround = { /*TODO*/ },
        startDestination = ChooseLoginMethod,
        torangLogo = { TorangLogo("T O R A N G", "hit the spot") },
        signInScreen = {
            PreviewSignInScreen()
        }, onSignUp = {}

    )
}

@Preview
@Composable
fun SignInSignUpScreen1Preview() {
    SignInSignUpScreen(/*Preview*/
        isLogin = false,
        showTopBar = false,
        onBack = { /*TODO*/ },
        onSuccessLogin = { /*TODO*/ },
        onLookAround = { /*TODO*/ },
        startDestination = SignIn,
        signInScreen = {
            PreviewSignInScreen()
        },
        torangLogo = {
            TorangLogo("T O R A N G", "hit the spot")
        },
        onSignUp = {}
    )
}