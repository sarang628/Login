package com.sarang.torang.compose.signinsignup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signin.PreviewSignInScreen
import com.sarang.torang.compose.signinsignup.signin.SignInScreen
import com.sarang.torang.screens.login.ChooseLoginMethod
import com.sarang.torang.screens.login.SignIn
import kotlinx.coroutines.delay

@Composable
fun SignInSignUpScreen(
    viewModel: SignInSignUpViewModel = hiltViewModel(),
    onSuccessLogin: () -> Unit,
    showTopBar: Boolean,
    onBack: (() -> Unit),
    onLookAround: () -> Unit,
    showLookAround: Boolean = true,
    onSignUp: () -> Unit,
    startDestination: Any = ChooseLoginMethod,
    signInScreen: @Composable () -> Unit = { SignInScreen() },
    torangLogo: @Composable (() -> Unit) = { TorangLogo() },
    navController: NavHostController = rememberNavController(),
) {
    val isLogin by viewModel.isLogin.collectAsState(false)

    LaunchedEffect(key1 = isLogin) {
        if (isLogin) onSuccessLogin.invoke()
    }

    _SignInSignUpScreen(
        isLogin = isLogin,
        showTopBar = showTopBar,
        onBack = onBack,
        onLookAround = onLookAround,
        showLookAround = showLookAround,
        onSignUp = onSignUp,
        startDestination = startDestination,
        signInScreen = signInScreen,
        torangLogo = torangLogo,
        navController = navController,
        onEmailLogin = { navController.navigate(SignIn) }
    )
}


/**
 * @param isLogin 로그인 여부
 * @param showTopBar 상단바를 보여줄지 여부
 * @param onBack 뒤로가기 버튼을 눌렀을 때 호출되는 콜백
 * @param onSuccessLogin 로그인 성공시 호출되는 콜백
 * @param onLookAround 둘러보기 버튼을 눌렀을 때 호출되는 콜백
 * @param showLookAround 둘러보기 버튼을 보여줄지 여부
 * @param onSignUp 회원가입 버튼을 눌렀을 때 호출되는 콜백
 * @param startDestination 로그인/회원가입 선택화면 시작점
 * @param signInScreen 로그인 화면
 * @param torangLogo Torang 로고
 * @param navController 네비게이션 컨트롤러
 */
@Composable
fun _SignInSignUpScreen(
    isLogin: Boolean,
    showTopBar: Boolean,
    onBack: (() -> Unit),
    onLookAround: () -> Unit,
    showLookAround: Boolean = true,
    onSignUp: () -> Unit,
    onEmailLogin: () -> Unit,
    startDestination: Any = ChooseLoginMethod,
    signInScreen: @Composable () -> Unit = { SignInScreen() },
    torangLogo: @Composable (() -> Unit) = { TorangLogo() },
    navController: NavHostController = rememberNavController(),
) {
    var currentScreen: String? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = "") {
        navController.currentBackStackEntryFlow.collect {
            currentScreen = it.destination.route
        }
    }

    TorangLogoScaffold(
        isLogin = isLogin,
        showTopBar = showTopBar || currentScreen?.isEmailLoginScreen() == true,
        onBack = {
            if (!navController.popBackStack())
                onBack.invoke()
        },
        torangLogo = torangLogo,
    ) {
        NavHost( //navigation
            navController = navController,
            startDestination = startDestination
        ) {
            composable<ChooseLoginMethod> {
                SignInSignUpComponent(
                    onEmailLogin = onEmailLogin,
                    onSignUp = onSignUp,
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

/**
 * @param isLogin 로그인 여부
 * @param showTopBar 상단바를 보여줄지 여부
 * @param onBack 뒤로가기 버튼을 눌렀을 때 호출되는 콜백
 * @param torangLogo Torang 로고
 * @param contents 화면 내용
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TorangLogoScaffold(
    isLogin: Boolean,
    showTopBar: Boolean = false,
    onBack: (() -> Unit)? = null,
    torangLogo: @Composable (() -> Unit) = { TorangLogo() },
    contents: @Composable (() -> Unit),
) {
    // @formatter:off
    Scaffold(
        topBar = {
            if (showTopBar) SignInSignUpTopAppBar(onBack = { onBack?.invoke() })
            else TopAppBar(title = {})
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
            torangLogo.invoke() // 로고 표시
            if (!isLogin) contents.invoke() // 로그인이 아닐 경우 화면 표시
        }
    }
    // @formatter:on
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
            IconButton(
                modifier = Modifier.testTag("btnNavUp"),
                onClick = {
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

        Button(modifier = Modifier.testTag("btnLogin"), onClick = { onEmailLogin.invoke() }) {
            Text(text = stringResource(id = R.string.login_with_email))
        }
        /*sign up*/
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.dont_have_an_account))
            TextButton(modifier = Modifier.testTag("btnSignUp"), onClick = { onSignUp.invoke() }) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        /*Look Around*/
        if (showLookAround) {
            TextButton(
                modifier = Modifier.testTag("btnLookAround"),
                onClick = { onLookAround.invoke() }) {
                Text(
                    text = stringResource(id = R.string.look_around),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * Torang 로고
 * @param previewTitle 미리보기용 제목
 * @param previewSubtitle 미리보기용 부제
 */
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

@Preview
@Composable
fun SignInSignUpScreenPreview() {
    _SignInSignUpScreen(
        /*Preview*/
        isLogin = false,
        showTopBar = false,
        onBack = { },
        onLookAround = { },
        startDestination = ChooseLoginMethod, // SignIn/ChooseLoginMethod
        torangLogo = { TorangLogo("T O R A N G", "hit the spot") },
        signInScreen = { PreviewSignInScreen() },
        onSignUp = { },
        onEmailLogin = { },
    )
}

@Preview
@Composable
fun SignInSignUpScreenIsLoginPreview() {
    _SignInSignUpScreen(
        /*Preview*/
        isLogin = true,
        showTopBar = false,
        onBack = { },
        onLookAround = { },
        startDestination = ChooseLoginMethod, // SignIn/ChooseLoginMethod
        torangLogo = { TorangLogo("T O R A N G", "hit the spot") },
        signInScreen = { PreviewSignInScreen() },
        onSignUp = { },
        onEmailLogin = { },
    )
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
fun SignInSignUpScreen1Preview() {
    _SignInSignUpScreen(/*Preview*/
        isLogin = true,
        showTopBar = false,
        onBack = { /*TODO*/ },
        onLookAround = { /*TODO*/ },
        startDestination = SignIn,
        signInScreen = {
            PreviewSignInScreen()
        },
        torangLogo = {
            TorangLogo("T O R A N G", "hit the spot")
        },
        onSignUp = {},
        onEmailLogin = {}
    )
}