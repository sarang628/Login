package com.sarang.torang.viewmodels

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.R
import com.sarang.torang.compose.signinsignup.signup.SignUpViewModel
import com.sarang.torang.usecase.CheckEmailDuplicateUseCase
import com.sarang.torang.usecase.ConfirmCodeUseCase
import com.sarang.torang.usecase.VerifyEmailFormatUseCase
import com.sarang.torang.usecase.VerifyPasswordFormatUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignUpViewModelTest {

    // @formatter:off

    // Hilt 테스트 룰 설정
    @get:Rule var hiltRule = HiltAndroidRule(this)

    // Compose 테스트 룰 설정
    @get:Rule val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> = createAndroidComposeRule<ComponentActivity>()

    // 필요한 UseCase들 주입
    @Inject lateinit var emailUseCase: CheckEmailDuplicateUseCase
    @Inject lateinit var validEmailUseCase: VerifyEmailFormatUseCase
    @Inject lateinit var validPasswordUseCase: VerifyPasswordFormatUseCase

    // 에러 메시지 문자열 변수
    private lateinit var errorEmailValid: String
    private lateinit var passwordFormatError: String

    // ViewModel 초기화 변수
    private lateinit var viewModel: SignUpViewModel

    // confirmCodeUseCase의 모킹 구현
    private val confirmCodeUseCase: ConfirmCodeUseCase
        get() = object : ConfirmCodeUseCase {
            override suspend fun confirmCode(
                token: String,
                confirmCode: String,
                name: String,
                email: String,
                password: String,
            ): Boolean {
                return true // 항상 true를 반환하는 테스트용 구현
            }
        }


    @Before
    fun init() {
        // Hilt 주입
        hiltRule.inject()

        // ViewModel 초기화
        viewModel = SignUpViewModel(confirmCodeUseCase, validEmailUseCase, validPasswordUseCase, emailUseCase)

        // 에러 메시지 문자열 초기화
        errorEmailValid = composeTestRule.activity.getString(R.string.error_email_valid)
        passwordFormatError = composeTestRule.activity.getString(R.string.password_format_error)
    }

    // @formatter:on

    @Test
    fun inputTest() {
        // 초기 상태에서 name 필드가 빈 문자열인지 확인
        assertEquals("", viewModel.uiState.name)
        // 이름을 "test123"으로 변경
        viewModel.onChangeName("test123")
        // name 필드가 "test123"으로 변경되었는지 확인
        assertEquals("test123", viewModel.uiState.name)
        // name 필드를 비운다
        viewModel.clearName()
        // name 필드가 다시 빈 문자열인지 확인
        assertEquals("", viewModel.uiState.name)

        // 초기 상태에서 password 필드가 빈 문자열인지 확인
        assertEquals("", viewModel.uiState.password)
        // 비밀번호를 "aabbccdd"로 변경
        viewModel.onChangePassword("aabbccdd")
        // password 필드가 "aabbccdd"로 변경되었는지 확인
        assertEquals("aabbccdd", viewModel.uiState.password)
        // password 필드를 비운다
        viewModel.clearPassword()
        // password 필드가 다시 빈 문자열인지 확인
        assertEquals("", viewModel.uiState.password)

        // 초기 상태에서 confirmCode 필드가 빈 문자열인지 확인
        assertEquals("", viewModel.uiState.confirmCode)
        // 확인 코드를 "101010101010"으로 변경
        viewModel.onChangeConfirmationCode("101010101010")
        // confirmCode 필드가 "101010101010"으로 변경되었는지 확인
        assertEquals("101010101010", viewModel.uiState.confirmCode)
        // confirmCode 필드를 비운다
        viewModel.clearConfirmationCode()
        // confirmCode 필드를 다시 빈 문자열로 변경
        viewModel.onChangeConfirmationCode("")

        // 초기 상태에서 email 필드가 빈 문자열인지 확인
        assertEquals("", viewModel.uiState.email)
        // 이메일을 "@@@@@aaaaaavvvccc"로 변경
        viewModel.onChangeEmail("@@@@@aaaaaavvvccc")
        // email 필드가 "@@@@@aaaaaavvvccc"로 변경되었는지 확인
        assertEquals("@@@@@aaaaaavvvccc", viewModel.uiState.email)
        // email 필드를 비운다
        viewModel.clearEmail()
        // email 필드가 다시 빈 문자열인지 확인
        assertEquals("", viewModel.uiState.email)
    }

    @Test
    fun invalidEmailFormatTest() {
        runBlocking {
            viewModel.onChangeEmail("")
            // 초기 상태의 이메일 에러 메시지가 null인지 확인
            assertNull(viewModel.uiState.emailErrorMessage)
            // 이메일 등록 시도
            viewModel.registerEmail()
            // 이메일 유효성 검증 에러 메시지가 올바르게 설정되었는지 확인
            assertEquals(errorEmailValid, viewModel.uiState.emailErrorMessage)
        }
    }

    @Test
    fun invalidPasswordFormatTest() {
        runBlocking {
            // 초기 상태의 비밀번호 에러 메시지가 null인지 확인
            assertNull(viewModel.uiState.passwordErrorMessage)
            // 비밀번호 유효성 검증 시도
            viewModel.validPassword()
            // 디버그 로그 출력
            Log.d("_SignUpViewModelTest", viewModel.uiState.toString())
            // 비밀번호 유효성 검증 에러 메시지가 올바르게 설정되었는지 확인
            assertEquals(
                passwordFormatError,
                viewModel.uiState.passwordErrorMessage
            )
        }
    }

    @Test
    fun duplicateEmailTest() {
        runBlocking {
            // 초기 상태의 이메일 에러 메시지가 null인지 확인
            assertNull(viewModel.uiState.emailErrorMessage)
            // 이메일 및 비밀번호 변경
            viewModel.onChangeEmail("sarang628@naver.com")
            viewModel.onChangePassword("aaaaa")
            // 이메일 등록 시도
            viewModel.registerEmail()
            // 중복 이메일 검증을 위해 지연
            delay(2000)
            // 이메일 중복 검증 에러 메시지가 올바르게 설정되었는지 확인
            assertEquals("등록된 이메일 입니다.", viewModel.uiState.emailErrorMessage)
        }
    }

    @Test
    fun confirmCodeTest() {
        runBlocking {
            // 초기 상태의 인증 번호 확인 플래그가 false인지 확인
            assertFalse(viewModel.uiState.checkedConfirm)
            // 인증 번호 확인 시도
            viewModel.confirmCode()
            // 인증 번호 검증을 위해 지연
            delay(1000)
            // 인증 번호 확인 플래그가 true로 설정되었는지 확인
            assertTrue(viewModel.uiState.checkedConfirm)
        }
    }

}