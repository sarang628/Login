# [이론 정리]()
안드로이드 웹사이트에 

# [SignInScreen](../../library/src/main/java/com/sarang/torang/compose/signinsignup/signin/SignInScreen.kt)

로그인 화면의 UI elements를 작성한 파일이다.</br>
Jetpack Compose를 사용했다.</br>

# [SignInUiState](../../library/src/main/java/com/sarang/torang/compose/signinsignup/signin/SignInViewModel.kt)

UI state는 사용자에게 표현하기 위한 정보이다.</br>
UI를 작성하다 보면 자연스럽게 UIState로 변경해 주어야 할 데이터들이 생긴다.</br>
로그인 화면에서는 사용자에게 입력받는 email과 password가 있었다.</br>
로그인 중 progressBar을 보여 준다던가</br>
로그인 실패 시 오류 메시지를 보여 주어야 할 때도 UIState로 바꿔 주어야 했다.</br>

```
data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isProgress: Boolean = false,
    val error: String? = null,
    val emailErrorMessage: LoginErrorMessage? = null,
    val passwordErrorMessage: LoginErrorMessage? = null,
)
```

# [SignInViewModel](../../library/src/main/java/com/sarang/torang/compose/signinsignup/signin/SignInViewModel.kt)

안드로이드 UILayer에는 state holder라는 개념이 있다. 
UI state는 기본적으로 불변 데이터이다.</br> 
데이터를 새롭게 생성하여 데이터를 변경 할 수 있다.</br>
하지만 UI state는 사용자 입력이나 이벤트에 따라 변화가 필요하다.</br>
이런 UI state를 생성하고 관리하는 적절한 곳이 필요하다.</br>
state holder가 이런 역할을 한다.안드로이드에서 ViewModel 클래스가 state holder의 기능을 가지고 있다.

```
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val emailLoginService: EmailLoginUseCase,
    private val emailUseCase: ValidEmailUseCase,
    private val passwordUseCase: ValidPasswordUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(SignInUiState())
        private set

    private var fetchJob: Job? = null
    
    fun login(onLogin: () -> Unit) {
        if (!validateIdPw()) return;
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            clearErrorMsg()
            showProgress(true)
            try {
                emailLoginService.invoke(uiState.email, uiState.password) // 이메일 로그인 API 호출
                onLogin.invoke()
            } catch (e: Exception) {
                showError(e.message!!)
            } finally {
                showProgress(false)
            }
        }
    }
    
    private fun validateIdPw(): Boolean
    private fun showPasswordErrorMessage(message: LoginErrorMessage)
    private fun showEmailErrorMessage(message: LoginErrorMessage)
    private fun clearValidErrorMessage()
    private fun showProgress(b: Boolean)
    private fun showError(error: String)
    
    fun onChangeEmail(email: String)
    fun onChangePassword(password: String)
    fun clearEmail()
    fun clearErrorMsg()
}
```