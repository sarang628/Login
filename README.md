# Login Module

<img src="screenshots/screen.jpg" alt=""/>

## Requirement

### functional

- 사용자는 이메일 주소와 비밀번호를 입력하여 로그인할 수 있습니다.
- 사용자는 "회원가입" 버튼을 클릭하여 회원가입 화면으로 이동할 수 있습니다.
- 사용자는 "둘러보기" 버튼을 클릭하여 로그인하지 않고도 앱을 둘러볼 수 있습니다.
- 사용자는 이메일 주소, 비밀번호, 또는 소셜 계정으로 로그인할 수 있습니다.
- 사용자는 로그인에 실패한 경우 오류 메시지를 확인할 수 있습니다.
- 사용자는 계정 찾기 또는 비밀번호 찾기 기능을 사용하여 계정에 액세스할 수 있습니다.

- 이메일/비밀번호 로그인
    - 사용자는 이메일 주소와 비밀번호를 입력하여 로그인할 수 있습니다.
    - 이메일 주소는 필수 입력 항목입니다.
    - 비밀번호는 필수 입력 항목입니다.
    - 로그인에 성공한 경우, 사용자는 앱의 메인 화면으로 이동합니다.
    - 로그인에 실패한 경우, 사용자는 오류 메시지를 확인할 수 있습니다.

- 소셜 계정 로그인
    - 사용자는 Facebook, Google, 또는 Twitter 계정으로 로그인할 수 있습니다.
    - 로그인에 성공한 경우, 사용자는 앱의 메인 화면으로 이동합니다.
    - 로그인에 실패한 경우, 사용자는 오류 메시지를 확인할 수 있습니다.

- 계정 찾기/비밀번호 찾기
    - 사용자는 계정 찾기 또는 비밀번호 찾기 기능을 사용하여 계정에 액세스할 수 있습니다.
    - 계정 찾기 기능을 사용하면, 사용자는 이메일 주소를 입력하여 계정 정보를 확인할 수 있습니다.
    - 비밀번호 찾기 기능을 사용하면, 사용자는 이메일 주소와 계정 이름을 입력하여 비밀번호를 재설정할 수 있습니다.

### Non-functional

- 로그인 화면은 모든 기기에서 최적의 화면 크기로 표시되어야 합니다.
- 로그인 화면의 버튼은 사용자에게 명확하게 구분되어야 합니다.
- 로그인 화면은 사용하기 쉽고 직관적이어야 합니다.

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

## Convention

### package

<img src="screenshots/package.png" width="40%" height="40%" alt=""/>

### Function

- Login + Screen
- SignUp + Screen
- EmailLogin + Screen

- EmailLogin + UseCase
- IsLoginFlow + UseCase
- Logout + UseCase
- SignUp + UseCase

- EmailLogin + ViewModel
- Login + ViewModel
- SignUp + ViewModel

## Architecture

### UI Layer

#### UI element

UI elements such as activities and fragments that display the data

- OutlinedTextField
    - supportingText : 입력 필드 하단에 작은 글씨로 정보 제공
    - trailingIcon : 입력 필드 끝에 아이콘 배치
    - keyboardOptions : 키보드 엔터 자리 다른 버튼으로 변경 가능
    - keyboardActions : 탭키나 엔터 키 눌렀을 때 다음 필드로 입력 가능하게 설정 가능

#### UI state

- The UI state is what the app says they should see.
- UI can be encapsulated in a UiState data class

```
data class EmailLoginUiState(
    val email: String = "",
    val password: String = "",
    val isProgress: Boolean = false,
    val error: String? = null,
    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null
)
```

```
data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val confirmCode: String = "",
    val password: String = "",
    val isProgress: Boolean = false,
    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,
    val confirmCodeErrorMessage: String? = null
)
```

#### State holder(ViewModel)

Responsible for the production of UI state and contain the necessary logic for that task

```
```

## Illustrating cycle of event

<img src="screenshots/event.png" alt=""/>

## UnitTest

## What was difficult

## Preview

<img src="screenshots/preview.gif" alt=""/>