https://developer.android.com/topic/architecture/ui-layer

The role of the UI?
display the application data on the screen
primary point of user interaction

The UI layer?
the pipeline that converts application data changes to a form
(that the UI can present and then displays it.)

# [UI layer architecture](https://developer.android.com/topic/architecture/ui-layer#architecture)
1. Consume app data and transform it into data the UI can easily render.
2. Consume UI-renderable data and transform it into UI elements for presentation to the user.
3. Consume user input events from those assembled UI elements and reflect their effects in the UI data as needed.
4. Repeat steps 1 through 3 for as long as necessary.

# [Define UI state](https://developer.android.com/topic/architecture/ui-layer#define-ui-state)
What is UI state?
information that the app presents to the user

UI vs UIState?
UI is what the user sees
UI state is what the app says they should see
UI is the visual representation of the UI state


# [Manage state with Unidirectional Data Flow](https://developer.android.com/topic/architecture/ui-layer#udf)


# EmailLoginState
```
data class EmailLoginUiState(
    val email: String = "",
    val password: String = "",
    val isProgress: Boolean = false,
    val error: String? = null,
    val emailErrorMessage: LoginErrorMessage? = null,
    val passwordErrorMessage: LoginErrorMessage? = null,
)
```
