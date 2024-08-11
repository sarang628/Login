# 로그인 API가 제대로 동작하는지 확인

[EmailLoginUseCaseTest](/app/src/androidTest/java/com/sarang/torang/api/EmailLoginUseCaseTest.kt)

```
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class EmailLoginUseCaseTest {

    // Hilt를 사용한 의존성 주입 설정
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // EmailLoginUseCase를 주입 받음
    @Inject
    lateinit var emailLoginService: EmailLoginUseCase

    // 테스트 전에 실행되는 초기화 함수
    @Before
    fun init() {
        // Hilt를 통해 의존성 주입
        hiltRule.inject()
    }

    // 로그인 성공 테스트
    @Test
    fun successLoginTest() {
        runBlocking {
            // 이메일 및 비밀번호 설정 // 로그인 시도
            try {
                emailLoginService.invoke("sarang628@naver.com", "Torang!234")
                // 일정 시간 대기 (네트워크 요청 가정)
                delay(3000)
            } catch (e: Exception) {
                // 에러 메시지 확인
                Assert.assertEquals("", e.message)
            }
        }
    }

    // 로그인 실패 테스트
    @Test
    fun wrongLoginTest() {
        runBlocking {
            // 이메일 및 비밀번호 설정 // 로그인 시도
            try {
                emailLoginService.invoke("sarang628@naver.com", "Torang!2345")
                // 일정 시간 대기 (네트워크 요청 가정)
                delay(3000)
            } catch (e: Exception) {
                // 에러 메시지 확인
                Assert.assertEquals("로그인에 실패하였습니다.", e.message)
            }
        }
    }
}
```