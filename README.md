# Login Module

<img src="screenshots/screen.jpg" alt=""/>


## 모듈 설명
앱의 로그인과 회원가입 기능을 제공하는 모듈이다.
인스타그램의 ui와 flow를 참조했다.

## 개발 환경 구성
- app: login module를 실행하고 테스트하는 앱
- library: login module이 실제 구현되어있는 라이브러리
- di: app에 위치하며 실제 환경에서 테스트를 위한 필수 코드
  - repository - 저장소(API, DB) 모듈을 주입하여 사용
  - login - login 모듈을 사용하는데 있어 필요한 의존성 정의

라이브러리 개발 시 사용하는 모듈
- repository - 실제 앱의 저장소(API, DB 등)가 필요 시 사용 
- theme - 화면의 공통 UI를 적용하는데 사용

# Feature
- [Android Jetpack's Navigation component](./Documents/Navigation.md)
- [JetPack Compose](./Documents/JetpackCompose.md)
- [Android Architecture Component](./Documents/JetpackCompose.md)


## 코드 설명

### LoginNavHost.kt
로그인 화면의 경로들을 정의. 실질적인 화면의 시작점 이다.
SignInSignUpExploreNavHost - 로그인, 회원가입 선택화면
SignUpNavHost - 회원 가입 화면
가 선언되어 있다.

### SignInSignUpExploreNavHost.kt
로그인 회원가입 선택화면.
SignInSignUpExplore

### SignInSignUpExplore.kt
로그인 회원가입 선택 UI

### Screen Definition

- LoginNavHost
  - 로그인 방법 선택 (현재 이메일만 있음.)
  - 회원가입 화면 이동
- SignUpNavHost
  - 회원가입 네비게이션 화면
  - 이름 입력
  - 이메일 입력
  - 비밀번호 (확인) 입력
  - 회원가입 성공
- LoginChooseMethodNavHost
  - 이메일 로그인, 둘러보기, 회원가입 버튼
- EmailLoginScreen
  - 이메일 입력하여 로그인

## UnitTest
### package
<img src="screenshots/unittest_package.png" alt=""/>

### compose test
- 문구들이 나오는지 주로 작성
- 파라미터에 따라 navigation up 버튼 나오는지 테스트
```
@RunWith(AndroidJUnit4::class)
class SignInSignUpExploreNavHostTest {

//    @get:Rule
//    val composeTestRule = createComposeRule()

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUiState() {
        composeTestRule.setContent {
            SignInSignUpExploreNavHost(
                showTopBar = true,
                onLookAround = {},
                onSignUp = {},
                isLogin = false,
                showLookAround = true
            )
        }
    }

    @Test
    fun showTopBarTest() {
        composeTestRule.onNodeWithContentDescription(
            label = composeTestRule.activity.getString(R.string.a11y_back)
        ).assertIsDisplayed()
    }

    @Test
    fun checkEmailElement() {
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.login_with_email)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.login_with_email)
        ).performClick()
    }

    /*@Test
    fun checkHitTheSpotElement() {
        composeTestRule.onNodeWithText("Hit the spot").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hit the spot").performClick()
    }*/

    @Test
    fun checkSignUpElement() {
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.sign_up)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.sign_up)
        ).performClick()
    }

    @Test
    fun checkLookAroundElement() {
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.look_around)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.look_around)
        ).performClick()
    }
}
```
### viewmodel test
- 잘못된 형식 이메일 입력 시 오류 데이터 발생하는지 확인
- 로그인 정보 입력하여 API 테스트
```
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class EmailLoginViewModelTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var emailLoginService: EmailLoginUseCase

    @Inject
    lateinit var emailUseCase: ValidEmailUseCase

    @Inject
    lateinit var passwordUseCase: ValidPasswordUseCase

    private lateinit var viewModel: EmailLoginViewModel

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = EmailLoginViewModel(
            emailUseCase = emailUseCase,
            emailLoginService = emailLoginService,
            passwordUseCase = passwordUseCase
        )
    }

    @Test
    fun invalidEmail() {
        runBlocking {
            viewModel.login(onLogin = {})
            //do business logic
            Assert.assertEquals("Error", viewModel.uiState.value.emailErrorMessage)
        }
    }

    @Test
    fun invalidPassword() {
        runBlocking {
            viewModel.login(onLogin = {})
            //do business logic
            Assert.assertEquals("Error", viewModel.uiState.value.passwordErrorMessage)
        }
    }

    @Test
    fun wrongLogin() {
        runBlocking {
            viewModel.onChangeEmail("sarang628@naver.com")
            viewModel.onChangePassword("bbbbb")
            viewModel.login(onLogin = {

            })
            //do business logic by api call
            delay(3000)
            Assert.assertEquals("로그인에 실패하였습니다.", viewModel.uiState.value.error)
        }
    }

    @Test
    fun collectLogin() {
        runBlocking {
            viewModel.onChangeEmail("sarang628@naver.com")
            viewModel.onChangePassword("aaaaa")
            viewModel.login(onLogin = {

            })
            //do business logic by api call
            delay(3000)
            Assert.assertEquals(null, viewModel.uiState.value.error)
        }
    }
}
```

## What was difficult
- 처음 적용해보는 Unitest 코드 
  - UI가 제대로 나오는지 확인, API 호출 테스트 등
  - 로직 변경 시 unittest를 통해 먼저 테스트 할 수 있어 앱에서 실제로 동작하지 않아도 오류를 미리 파악 할 수 있어 유용
- 레이아웃 최적화
  - 회원 가입 화면에서 페이지 이동시마다 TopAppBar를 복붙으로 다 적용해 비효율적
  - 바깥으로 빼는 과정을 작업해줘야 해서 작업시간이 길어짐
  - 빠르게 화면을 만드는것도 중요하지만 개발 전 조금 더 생각 필요성 느낌
- 생각보다 신경써야 할 게 많은 로직
  - 회원가입 시 마다 입력 값에 대한 유효성 체크 및 UI 표시
  - 이메일 인증 등

## Preview
<img src="screenshots/preview.gif" alt=""/>
