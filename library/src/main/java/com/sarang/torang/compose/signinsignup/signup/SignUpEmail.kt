package com.sarang.torang.compose.signinsignup.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
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

/**
 * 회원가입 이메일 입력
 * @param email 이메일
 * @param checkedEmailDuplication 이메일 중복 검사
 * @param errorMessage 에러 메시지
 * @param onValueChange 이메일 변경 콜백
 * @param onBack 이전 버튼 콜백
 * @param onClear 이메일 삭제 콜백
 * @param onNext 다음 버튼 콜백
 * @param onVerifiedEmail 유효한 이메일 검사 완료 콜백
 */
@Composable
internal fun SignUpEmail(
    email: String,
    checkedEmailDuplication: Boolean,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
    onNext: () -> Unit,
    onVerifiedEmail: () -> Unit,
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(key1 = checkedEmailDuplication, lifecycle) {
        snapshotFlow { checkedEmailDuplication }
            .filter { it }
            .flowWithLifecycle(lifecycle)
            .collect {
                onVerifiedEmail()
            }
    }

    Scaffold(
        topBar = { NavBackTopAppBar(onBack = onBack) },
        contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp)
    ) {
        Column(Modifier.padding(it)) {

            Text( // title
                modifier = Modifier.padding(bottom = 6.dp),
                text = stringResource(id = R.string.what_s_your_email),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text( // content
                modifier = Modifier.padding(vertical = 6.dp),
                text = stringResource(id = R.string.describe_input_email)
            )
            SignCommonTextField( // input email
                modifier = Modifier.padding(vertical = 6.dp),
                label = stringResource(id = R.string.label_email),
                value = email,
                onValueChange = onValueChange,
                placeHolder = stringResource(id = R.string.label_email),
                onClear = onClear,
                errorMessage = errorMessage
            )
            Button( // next button
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                onClick = onNext::invoke
            ) {
                Text(text = stringResource(id = R.string.label_next))
            }
        }
    }
}


@Preview
@Composable
fun PreviewSignUpEmail() {
    SignUpEmail(/*Preview*/
        email = "",
        errorMessage = "aa",
        onClear = {},
        onValueChange = {},
        onBack = {},
        onNext = {},
        onVerifiedEmail = {},
        checkedEmailDuplication = false

    )
}