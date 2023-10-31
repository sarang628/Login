package com.sarang.toringlogin.login.compose.email

import android.util.Log
import android.view.KeyEvent.ACTION_DOWN
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.toringlogin.R
import com.sarang.toringlogin.login.viewmodels.EmailLoginViewModel

@Composable
fun EmailLoginInput(
    onLogin: (id: String, password: String) -> Unit,
    progress: Boolean,
    emailErrorMessage: String? = null,
    passwordErrorMessage: String? = null,
    email: String,
    password: String,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onClearEmail: () -> Unit,
    onClearPassword: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Box(Modifier.height(250.dp)) {
        ConstraintLayout {
            Spacer(modifier = Modifier.height(100.dp))
            Column(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginOutlinedTextField(
                    value = email,
                    onValueChange = onChangeEmail,
                    label = "Email",
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onKeyTabOrDown = { focusManager.moveFocus(FocusDirection.Down) },
                    placeHolder = "이메일을 입력해주세요.",
                    errorMessage = emailErrorMessage,
                    onClear = { onClearEmail.invoke() }
                )
                LoginOutlinedTextField(
                    value = password,
                    onValueChange = onChangePassword,
                    label = "Password",
                    onNext = { focusManager.clearFocus(true) },
                    onKeyTabOrDown = { focusManager.clearFocus(true) },
                    placeHolder = "비밀번호를 입력해주세요.",
                    errorMessage = passwordErrorMessage,
                    onClear = { onClearPassword.invoke() }

                )
                Spacer(modifier = Modifier.height(10.dp))
                LoginButton(
                    onClick = { onLogin.invoke(email, password) }, progress = progress
                )
            }
        }
    }
}

@Composable
fun LoginButton(onClick: () -> Unit, progress: Boolean, modifier: Modifier = Modifier) {
    Button(onClick = onClick, enabled = !progress, modifier = modifier.width(100.dp)) {
        if (progress)
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(25.dp),
                strokeWidth = 3.dp
            )
        else
            Text(text = "Login")
    }
}


@Preview
@Composable
fun PreviewLoginButton() {
    LoginButton(onClick = { /*TODO*/ }, progress = false)
}