package com.sarang.torang.compose.signinsignup.signin

import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.R
import com.sarang.torang.data.LoginErrorMessage

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onLogin: () -> Unit,
) {
    val uiState = viewModel.uiState
    SignInScreen(
        uiState = uiState,
        onLogin = { viewModel.login(onLogin) },
        onChangeEmail = { viewModel.onChangeEmail(it) },
        onChangePassword = { viewModel.onChangePassword(it) },
        onClearEmail = { viewModel.clearEmail() },
        onClearErrorMsg = { viewModel.clearErrorMsg() }
    )
}

@Composable
internal fun SignInScreen(
    uiState: SignInUiState,
    onLogin: () -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onClearEmail: () -> Unit,
    onClearErrorMsg: () -> Unit,
) {
    Box {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SignInForm(
                onLogin = onLogin,
                onChangeEmail = onChangeEmail,
                onChangePassword = onChangePassword,
                onClearEmail = onClearEmail,
                progress = uiState.isProgress,
                email = uiState.email,
                password = uiState.password,
                emailErrorMessage = if (uiState.emailErrorMessage != null) stringResource(id = uiState.emailErrorMessage.resId) else null,
                passwordErrorMessage = if (uiState.passwordErrorMessage != null) stringResource(id = uiState.passwordErrorMessage.resId) else null
            )
            uiState.error?.let {
                AlertDialog(onDismissRequest = { onClearErrorMsg.invoke() },
                    confirmButton = {
                        Button(onClick = { onClearErrorMsg.invoke() }) {
                            Text(text = stringResource(id = R.string.confirm))
                        }
                    },
                    title = {
                        Text(text = it, fontSize = 16.sp)
                    })
            }
        }
    }
}

@Preview
@Composable
fun PreviewEmailLoginScreen() {
    SignInScreen(
        //uiState = EmailLoginUiState(),
        uiState = SignInUiState(
            //error = "로그인에 실패하였습니다.",
            email = "torang@torang.com",
            password = "password",
            emailErrorMessage = LoginErrorMessage.InvalidEmail,
            passwordErrorMessage = LoginErrorMessage.InvalidPassword,
            isProgress = false
        ),
        onLogin = { },
        onClearEmail = {},
        onChangePassword = {},
        onChangeEmail = {},
        onClearErrorMsg = {}
    )
}

/**
 * 로그인 폼
 * @param onLogin 로그인 클릭
 * @param progress 로그인 프로그레스
 * @param emailErrorMessage 이메일 입력 에러 메세지
 * @param passwordErrorMessage 비밀번호 입력 에러 메세지
 * @param email 이메일
 * @param password 비밀번호
 * @param onChangeEmail 이메일 입력
 * @param onChangePassword 비밀번호 입력
 * @param onClearEmail 이메일 초기화
 */
@Composable
fun SignInForm(
    onLogin: () -> Unit,
    progress: Boolean,
    emailErrorMessage: String? = null,
    passwordErrorMessage: String? = null,
    email: String,
    password: String,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onClearEmail: () -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInTextField(
                value = email,
                onValueChange = onChangeEmail,
                label = stringResource(id = R.string.label_email),
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onKeyTabOrDown = { focusManager.moveFocus(FocusDirection.Down) },
                placeHolder = stringResource(id = R.string.email_place_holder),
                errorMessage = emailErrorMessage,
                onClear = { onClearEmail.invoke() },
                enable = !progress
            )
            Spacer(modifier = Modifier.height(10.dp))
            SignInTextField(
                value = password,
                onValueChange = onChangePassword,
                label = stringResource(id = R.string.label_password),
                onNext = { focusManager.clearFocus(true) },
                onKeyTabOrDown = { focusManager.clearFocus(true) },
                placeHolder = stringResource(id = R.string.password_place_holder),
                errorMessage = passwordErrorMessage,
                onClear = { isPasswordVisible = !isPasswordVisible },
                isPassword = true,
                isPasswordVisual = isPasswordVisible,
                enable = !progress
            )
            Spacer(modifier = Modifier.height(15.dp))
            SignInButton(
                onClick = onLogin::invoke, progress = progress
            )
        }
    }
}

@Composable
fun SignInButton(onClick: () -> Unit, progress: Boolean, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        enabled = !progress,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        if (progress)
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(25.dp),
                strokeWidth = 3.dp
            )
        else
            Text(text = stringResource(id = R.string.label_login))
    }
}

@Composable
internal fun SignInTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    placeHolder: String,
    onKeyTabOrDown: (() -> Unit)? = null,
    onNext: (() -> Unit)? = null,
    onClear: () -> Unit,
    isPassword: Boolean = false,
    isPasswordVisual: Boolean = false,
    enable: Boolean? = null,
) {
    //에러 메시지를 필드 하단에 표시
    val compose = @Composable {
        Text(text = errorMessage ?: "", color = Color.Red)
    }

    Column {
        OutlinedTextField(
            label = { Text(text = label) },
            value = value,
            onValueChange = onValueChange,
            isError = !errorMessage.isNullOrEmpty(), //빨강 라인
            supportingText = if (errorMessage != null) compose else null,
            trailingIcon = { // 오류 아이콘
                if (errorMessage != null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_warning),
                        contentDescription = "",
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            if (enable != false)
                                onClear.invoke()
                        }
                    )
                } else if (isPassword) {
                    IconButton(
                        onClick = {
                            if (enable != false)
                                onClear.invoke()
                        }
                    ) {
                        Icon(
                            painter = if (!isPasswordVisual) painterResource(id = R.drawable.ic_password_off)
                            else painterResource(id = R.drawable.ic_password_on),
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                } else if (value.isNotEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_x),
                        contentDescription = "",
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            if (enable != false)
                                onClear.invoke()
                        }
                    )
                }
            },
            placeholder = { Text(text = placeHolder) }, // 힌트
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(14.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next), // 키보드 엔터 부분 Next로 바꾸기
            keyboardActions = KeyboardActions(onNext = { // Next를 눌렀을 경우
                onNext?.invoke()
            }),
            visualTransformation = if (isPassword && !isPasswordVisual) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .onPreviewKeyEvent { // 키보드 탭 또는 방향키 아래를 눌렀을 경우
                    if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN
                        || it.key == Key.NavigateNext && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN
                    ) {
                        Log.d("LoginOutlinedTextField", "onDown")
                        onKeyTabOrDown?.invoke()
                        true
                    } else {
                        false
                    }
                }
                .fillMaxWidth(),
            enabled = enable ?: true

        )
    }
}

@Preview
@Composable
fun PreviewLoginOutlinedTextField1() {
    SignInTextField(
        label = "label",
        onKeyTabOrDown = {},
        onValueChange = {},
        placeHolder = "placeHolder",
        value = "value",
        onNext = {},
        onClear = {},
        enable = false,
        errorMessage = "errorMessage"
    )
}


@Preview
@Composable
fun PreviewSignInButton() {
    SignInButton(onClick = { /*TODO*/ }, progress = false)
}