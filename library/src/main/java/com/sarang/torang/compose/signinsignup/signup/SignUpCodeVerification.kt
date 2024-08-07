package com.sarang.torang.compose.signinsignup.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.common.SignCommonTextField
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpCodeVerification(
    email: String,
    confirmCode: String,
    errorMessage: String? = null,
    checkedConfirm: Boolean,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    alertMessage: String? = null,
    onAlertDismiss: () -> Unit,
    onMoveBackEmail: () -> Unit,
    onVerifiedConfirm: () -> Unit,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(key1 = checkedConfirm, lifecycle) {
        snapshotFlow { checkedConfirm }
            .filter { it }
            .flowWithLifecycle(lifecycle)
            .collect {
                onVerifiedConfirm()
            }
    }

    Scaffold(
        topBar = { NavBackTopAppBar(onBack = onBack) },
        contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp)
    ) {
        Column(
            Modifier
                .padding(it)
        ) {
            Text(
                text = stringResource(id = R.string.enter_the_confirmation_code),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = stringResource(id = R.string.enter_the_confirmation_code_desc, email),
                modifier = Modifier.padding(vertical = 6.dp)
            )
            SignCommonTextField(
                modifier = Modifier
                    .testTag("tfConfirmCode")
                    .padding(vertical = 6.dp),
                label = stringResource(id = R.string.confirmation_code),
                value = confirmCode,
                onValueChange = onValueChange,
                placeHolder = stringResource(id = R.string.confirmation_code),
                onClear = onClear,
                errorMessage = errorMessage,
                showInputCount = true,
                limit = 6
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
                    .testTag("btnNext"),
                onClick = onNext::invoke
            ) {
                Text(text = stringResource(id = R.string.label_next))
            }
        }
    }
    alertMessage?.let {
        AlertDialog(
            onDismissRequest = { onAlertDismiss() },
            confirmButton = {
                Button(onClick = { onMoveBackEmail() }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
            text = {
                Text(text = it)
            })
    }
}


@Preview
@Composable
fun PreviewSignUpCodeVerification() {
    SignUpCodeVerification(
        /*Preview*/
        email = "torang@torang.com",
        confirmCode = "123456",
        onClear = {},
        onValueChange = {},
        onNext = {},
        onBack = {},
        onAlertDismiss = {},
        onMoveBackEmail = {},
        onVerifiedConfirm = {},
        checkedConfirm = false,
        errorMessage = "errorMessage",
//        alertMessage = "Are you sure you want to back?"
    )
}