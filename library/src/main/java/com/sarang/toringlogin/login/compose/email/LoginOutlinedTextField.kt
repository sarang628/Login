package com.sarang.toringlogin.login.compose.email

import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.toringlogin.R

@Composable
fun LoginOutlinedTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    placeHolder: String,
    onKeyTabOrDown: (() -> Unit)? = null,
    onNext: (() -> Unit)? = null,
    onClear: () -> Unit
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
                            onClear.invoke()
                        }
                    )
                } else if (value.isNotEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_x),
                        contentDescription = "",
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
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
                .fillMaxWidth()

        )
    }
}

@Preview
@Composable
fun PreviewLoginOutlinedTextField() {
    LoginOutlinedTextField(
        label = "",
        onKeyTabOrDown = {},
        onValueChange = {},
        placeHolder = "",
        value = "",
        onNext = {},
        onClear = {}
    )
}