package com.sarang.torang.viewmodels

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.signinsignup.signin.SignInViewModel
import com.sarang.torang.data.LoginErrorMessage
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.VerifyEmailFormatUseCase
import com.sarang.torang.usecase.VerifyPasswordFormatUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

// AndroidJUnit4를 사용하여 테스트 실행
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignInViewModelTest {

    // Hilt를 사용한 의존성 주입 설정
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // EmailLoginUseCase를 주입 받음
    @Inject
    lateinit var emailLoginService: EmailLoginUseCase

    // VerifyEmailFormatUseCase를 주입 받음
    @Inject
    lateinit var emailUseCase: VerifyEmailFormatUseCase

    // VerifyPasswordFormatUseCase를 주입 받음
    @Inject
    lateinit var passwordUseCase: VerifyPasswordFormatUseCase

    // SignInViewModel 객체 선언
    private lateinit var viewModel: SignInViewModel

    // 테스트 전에 실행되는 초기화 함수
    @Before
    fun init() {
        // Hilt를 통해 의존성 주입
        hiltRule.inject()
        // ViewModel 초기화
        viewModel = SignInViewModel(
            emailUseCase = emailUseCase,
            emailLoginUseCase = emailLoginService,
            passwordUseCase = passwordUseCase
        )
    }

    // 잘못된 이메일 테스트
    @Test
    fun invalidEmailTest() {
        runBlocking {
            // 로그인 시도
            viewModel.signIn()
            // 이메일 에러 메시지 확인
            Assert.assertEquals(LoginErrorMessage.InvalidEmail, viewModel.uiState.emailErrorMessage)
        }
    }

    // 잘못된 비밀번호 테스트
    @Test
    fun invalidPasswordTest() {
        runBlocking {
            // 로그인 시도
            viewModel.signIn()
            // 비밀번호 에러 메시지 확인
            Assert.assertEquals(LoginErrorMessage.InvalidPassword, viewModel.uiState.passwordErrorMessage)
        }
    }

    // 로그인 실패 테스트
    @Test
    fun wrongLoginTest() {
        runBlocking {
            // 이메일 및 비밀번호 설정
            setEmailAndPassword("sarang628@naver.com", "bbbbb")
            // 로그인 시도
            viewModel.signIn()
            // 일정 시간 대기 (네트워크 요청 가정)
            delay(3000)
            // 에러 메시지 확인
            Assert.assertEquals("로그인에 실패하였습니다.", viewModel.uiState.error)
        }
    }

    // 성공적인 로그인 테스트
    @Test
    fun successfulLoginTest() {
        runBlocking {
            // 올바른 이메일 및 비밀번호 설정
            setEmailAndPassword("sarang628@naver.com", "aaaaa")
            // 로그인 시도
            viewModel.signIn()
            // 일정 시간 대기 (네트워크 요청 가정)
            delay(3000)
            // 에러 메시지가 없는지 확인
            Assert.assertEquals(null, viewModel.uiState.error)
        }
    }

    // 이메일과 비밀번호를 설정하는 헬퍼 함수
    private fun setEmailAndPassword(email: String, password: String) {
        viewModel.onChangeEmail(email)
        viewModel.onChangePassword(password)
    }
}