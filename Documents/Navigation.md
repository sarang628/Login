# Android Jetpack's Navigation component 적용

https://developer.android.com/guide/navigation/principles
Principles of navigation의

- Fixed start destination
- Navigation state is represented as a stack of destinations
- Up and Back are identical within your app's task
- The Up button never exits your app
- Deep linking simulates manual navigation

https://developer.android.com/guide/navigation
Key concepts

- Host: 각종 화면을 불러오는 곳. 이곳을 통해서 화면 이동 및 뒤로가기 등 navigation 기능이 동작 (ex: LoginNavHost$NavHost)
- Graph: 전체 화면들이 navigation이 어떻게 구성을 보여줌(compose에서는 사용 하지 않는듯 하다.)
- Controller: host가 UI를 보여주는 곳이라면 controller은 실제 navigation(목적지, back stack, deep link 등)의 기능을 담당
- Destination: 각 화면을 정의하는 곳
- Route: 화면의 unique ID

## 경로 정의

주의 : navigation 2.8.0 이상 버전에서 동작한다.

```
    .build.gradle
    id 'org.jetbrains.kotlin.plugin.serialization' version '1.7.10' apply false

    id 'org.jetbrains.kotlin.plugin.serialization'

    implementation "androidx.constraintlayout:constraintlayout-compose:1.0.1"
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0"
```


```
@Serializable
object Profile
@Serializable
object FriendsList
```