package com.sarang.torang.compose.signinsignup.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.common.SignCommonTextField

/**
 * 회원가입 이름 입력
 * @param name 이름
 * @param errorMessage 에러 메시지
 * @param limit 이름 길이 제한
 * @param onValueChange 이름 변경 콜백
 * @param onClear 이름 삭제 콜백
 * @param onBack 이전 버튼 콜백
 * @param onNext 다음 버튼 콜백
 */
@Composable
fun SignUpName(
    name: String,
    errorMessage: Int? = null,
    limit: Int = 20,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = { NavBackTopAppBar(onBack = onBack) },
        contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp)
    ) {
        Column(
            Modifier.padding(it)
        ) {
            // contents
            Text(
                text = stringResource(id = R.string.what_s_your_name),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            //input name
            SignCommonTextField(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .testTag("tfName"),
                label = stringResource(id = R.string.label_full_name),
                value = name,
                onValueChange = onValueChange,
                placeHolder = stringResource(id = R.string.label_full_name),
                onClear = onClear,
                errorMessage = if (errorMessage != null) stringResource(id = errorMessage) else null,
                showInputCount = true,
                limit = limit
            )
            // next button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btnNext")
                    .padding(top = 4.dp),
                onClick = onNext
            ) {
                Text(text = stringResource(id = R.string.label_next))
            }
        }
    }
}

@Preview
@Composable
fun PreviewSignUpName() {
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