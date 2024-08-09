# Login Module

<img src="screenshots/screen.jpg" alt=""/>

맛집 공유 앱 토랑을 만들고 있습니다.</br>
이 프로젝트는 토랑에 로그인과 회원가입 UI를 제공 및 비지니스 로직을 확인하는 모듈입니다.

# Feature
## [UI Layer](Documents/ui_layer/UiLayerImplements.md)
UI Layer은 안드로이드 개발 아키텍처에서 제공하는 UI를 구현하는 방법 입니다.</br>Jetpack compose를 사용하여 UI Element를 구현하였고, UI State와 State Holder(ViewModel)을 사용해 UI 상태를 관리했습니다.
## [Navigation component](Documents/navigation/1_0Navigation.md)
Navigation Component는 안드로이드에서 제공하는 Navigation을 편리하게 다룰 수 있는 라이브러리 입니다.</br>
회원 가입과 로그인 화면은 여러 과정의 화면들로 구성되어있어 Navigation Component를 통해 가독성, 효율적, 편리하게 navigation을 처리하였습니다.
## [JetPack Compose](Documents/jetpack_compose/JetpackCompose.md)
Jetpack compose는 기존 XML을 사용한 view-based 기반의 화면 구현방식을 최근 front-end의 추세인<br>
선언형 UI model 방식을 적용한 새로운 UI 라이브러리 입니다.

## [Testing](Documents/test/Testing.md)
앱의 안정적인 개발과 운영을 위해 테스트 코드의 작성이 점점 중요해지고 있습니다.<br>
Compose - ViewModel - Repository 각 unit test부터 이를 결합한 integration test까지 구현하였습니다.

### [블로그 정리](https://srandroid.tistory.com/category/%ED%86%A0%EB%9E%91/%E3%84%B4%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85)

# Screen Navigation

```
[navigation]
LoginNavHost --- SignInSignUpScreen    --- SignInScreen
                 (로그인 회원가입 선택화면)  |   (이메일 로그인 화면)
                                       --- SignInSignUpExplore
                                           (회원가입 화면)
```

# 코드 설명

## LoginNavHost.kt

로그인 화면의 경로 정의. 화면의 시작점.
SignInSignUpExploreNavHost - 로그인, 회원가입 선택화면
SignUpNavHost - 회원 가입 화면

## SignInSignUpExploreNavHost.kt

로그인 회원가입 선택화면.
SignInSignUpExplore

## SignInSignUpExplore.kt

로그인 회원가입 선택 UI

# 개발 환경 구성

- app: login module을 실행하고 테스트하는 앱
- library: login module이 실제 구현되어 있는 라이브러리
- di: Data layer를 제공하는 필수 코드
    - repository - 저장소(API, DB) 구현 코드
    - login - login 모듈을 사용하는데 있어 필요한 의존성 작성

### module 개발에 필요한 다른 module

- repository - 실제 앱의 저장소(API, DB 등)가 필요 시 사용
- theme - 화면의 공통 UI를 적용 하는데 사용

# Difficulties

- 처음 적용해 보는 Unitest 코드
    - UI가 제대로 나오는지 확인, API 호출 테스트 등
    - 로직 변경 시 unittest를 통해 먼저 테스트할 수 있어 앱에서 실제로 동작하지 않아도 오류를 미리 파악할 수 있어 유용
- 레이아웃 최적화
    - 회원 가입 화면에서 페이지 이동 시마다 TopAppBar를 복붙으로 다 적용해 비효율적
    - TopAppBar를 재조정해서 작업 시간이 길어짐
    - 빠르게 화면을 만드는 것도 중요 하지만 개발 전 조금 더 생각 필요성 느낌
- 생각보다 신경 써야 할 게 많은 로직
    - 회원가입 시마다 입력값에 대한 유효성 체크 및 UI 표시
    - 이메일 인증 등

# Preview

<img src="screenshots/preview.gif" alt=""/>
