package com.sarang.torang

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sarang.torang.compose.signinsignup.signin.SignInViewModel
import com.sarang.torang.data.LoginErrorMessage
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.VerifyEmailFormatUseCase
import com.sarang.torang.usecase.VerifyPasswordFormatUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SignInViewModelTest {

    // LiveData의 값을 즉각적으로 설정하고 읽기 위해 사용
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // 코루틴 테스트를 위한 Rule 설정
    @get:Rule
    val coroutineRule = TestCoroutineRule()

    // Mock 객체 선언
    @Mock
    private lateinit var emailLoginUseCase: EmailLoginUseCase

    @Mock
    private lateinit var emailUseCase: VerifyEmailFormatUseCase

    @Mock
    private lateinit var passwordUseCase: VerifyPasswordFormatUseCase

    // ViewModel 객체 선언
    private lateinit var viewModel: SignInViewModel

    // 테스트 초기화 설정
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this) // Mock 객체 초기화
        viewModel = SignInViewModel(
            emailLoginUseCase = emailLoginUseCase,
            emailUseCase = emailUseCase,
            passwordUseCase = passwordUseCase
        )
    }

    // 유효하지 않은 이메일로 로그인 시도 시 이메일 오류 메시지 확인
    @Test
    fun `signIn with invalid email shows email error`() = runTest {
        `when`(emailUseCase.invoke(anyString())).thenReturn(false) // 이메일 검증 실패

        viewModel.signIn()

        assertEquals(LoginErrorMessage.InvalidEmail, viewModel.uiState.emailErrorMessage) // 이메일 오류 메시지 확인
    }

    // 유효하지 않은 비밀번호로 로그인 시도 시 비밀번호 오류 메시지 확인
    @Test
    fun `signIn with invalid password shows password error`() = runTest {
        `when`(emailUseCase.invoke(anyString())).thenReturn(true) // 이메일 검증 성공
        `when`(passwordUseCase.invoke(anyString())).thenReturn(false) // 비밀번호 검증 실패

        viewModel.signIn()

        assertEquals(LoginErrorMessage.InvalidPassword, viewModel.uiState.passwordErrorMessage) // 비밀번호 오류 메시지 확인
    }

    // 유효한 이메일과 비밀번호로 로그인 시도 시 이메일 로그인 UseCase 호출 확인
    @Test
    fun `signIn with valid email and password calls emailLoginUseCase`() = runTest {
        `when`(emailUseCase.invoke(anyString())).thenReturn(true) // 이메일 검증 성공
        `when`(passwordUseCase.invoke(anyString())).thenReturn(true) // 비밀번호 검증 성공

        viewModel.onChangeEmail("valid.email@example.com") // 이메일 설정
        viewModel.onChangePassword("ValidPassword123") // 비밀번호 설정
        viewModel.signIn()

        verify(emailLoginUseCase).invoke("valid.email@example.com", "ValidPassword123") // UseCase 호출 확인
    }

    // 로그인 실패 시 에러 메시지 설정 확인
    @Test
    fun `signIn failure sets error message`() = runTest {
        `when`(emailUseCase.invoke(anyString())).thenReturn(true) // 이메일 검증 성공
        `when`(passwordUseCase.invoke(anyString())).thenReturn(true) // 비밀번호 검증 성공
        `when`(emailLoginUseCase.invoke(anyString(), anyString())).thenThrow(RuntimeException("Login failed")) // 로그인 실패

        viewModel.onChangeEmail("valid.email@example.com") // 이메일 설정
        viewModel.onChangePassword("ValidPassword123") // 비밀번호 설정
        viewModel.signIn()

        advanceTimeBy(3000) // 시간 경과 시뮬레이션

        assertEquals("Login failed", viewModel.uiState.error) // 에러 메시지 확인
    }

    // 로그인 성공 시 에러 메시지가 없는지 확인
    @Test
    fun `signIn success clears error message`() = runTest {
        `when`(emailUseCase.invoke(anyString())).thenReturn(true) // 이메일 검증 성공
        `when`(passwordUseCase.invoke(anyString())).thenReturn(true) // 비밀번호 검증 성공

        viewModel.onChangeEmail("valid.email@example.com") // 이메일 설정
        viewModel.onChangePassword("ValidPassword123") // 비밀번호 설정
        viewModel.signIn()

        advanceTimeBy(3000) // 시간 경과 시뮬레이션

        assertNull(viewModel.uiState.error) // 에러 메시지가 없는지 확인
    }
}