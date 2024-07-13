package com.sarang.torang.compose.signinsignup.common

import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.R

/**
 * @param modifier Modifier
 * @param label 텍스트 필드 레이블
 * @param value 텍스트 필드 값
 * @param onValueChange 텍스트 필드 값 변경 시 호출되는 콜백
 * @param errorMessage 에러 메시지 (하단에 표시)
 * @param placeHolder 텍스트 필드 힌트
 * @param onKeyTabOrDown 키보드 탭 또는 방향키 아래를 눌렀을 때 호출되는 콜백
 * @param onNext Next를 눌렀을 때 호출되는 콜백
 * @param onClear 텍스트 필드 값을 지우는 콜백
 * @param isPassword 비밀번호인지 여부
 * @param isPasswordVisual 비밀번호 보이기 여부
 * @param enable 입력 가능 여부
 */
@Composable
internal fun SignCommonTextField(
    modifier: Modifier = Modifier,
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
    showInputCount: Boolean = false,
    limit: Int = Integer.MAX_VALUE,
) {

    val value1 = if (value.length >= limit) value.substring(0, limit) else value

    //에러 메시지를 필드 하단에 표시
    val supportingText: @Composable (() -> Unit)? =
        if (errorMessage != null || showInputCount) {
            @Composable {
                Box(modifier = Modifier.fillMaxWidth())
                {
                    if (errorMessage != null)
                        Text(text = errorMessage, color = Color.Red)

                    if (showInputCount)
                        Text(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            text = "(${value1.length}/${limit})",
                        )
                }
            }
        } else null

    Column {
        OutlinedTextField(
            label = { Text(text = label) },
            value = value1,
            onValueChange = onValueChange,
            isError = !errorMessage.isNullOrEmpty(), //빨강 라인
            supportingText = supportingText,
            trailingIcon = { // 오류 아이콘
                if (errorMessage != null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_warning),
                        contentDescription = "",
                        modifier = Modifier
                            .testTag("btnClear")
                            .clickable(
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
                        modifier = Modifier
                            .testTag("btnClear")
                            .clickable(
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
            modifier = modifier
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
            enabled = enable ?: true,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun test() {
    SignCommonTextField(
        modifier = Modifier,
        label = "Email",
        value = "email",
        onValueChange = {},
        errorMessage = "error",
        placeHolder = "email",
        onKeyTabOrDown = {},
        onNext = {},
        onClear = {},
        showInputCount = true
    )
}