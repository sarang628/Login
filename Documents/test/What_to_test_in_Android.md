# [What to test in Android](https://developer.android.com/training/testing/fundamentals/what-to-test)

# [Essential unit tests](https://developer.android.com/training/testing/fundamentals/what-to-test#essential)

- Unit tests for ViewModels, or presenters.
- Unit tests for the data layer, especially repositories. Most of the data layer should be
  platform-independent. Doing so enables test doubles to replace database modules and remote data
  sources in tests. See the guide on using test doubles in Android
- Unit tests for other platform-independent layers such as the Domain layer, as with use cases and
  interactors.
- Unit tests for utility classes such as string manipulation and math.

## [Testing Edge Cases](https://developer.android.com/training/testing/fundamentals/what-to-test#edge-cases)

- Math operations using negative numbers, zero, and boundary conditions.
- All the possible network connection errors.
- Corrupted data, such as malformed JSON.
- Simulating full storage when saving to a file.
- Object recreated in the middle of a process (such as an activity when the device is rotated).

## [Unit Tests to Avoid](https://developer.android.com/training/testing/fundamentals/what-to-test#avoid)

- Tests that verify the correct operation of the framework or a library, not your code.
- Framework entry points such as activities, fragments, or services should not have business logic
  so
  unit testing shouldn't be a priority. Unit tests for activities have little value, because they
  would cover mostly framework code and they require a more involved setup. Instrumented tests such
  as
  UI tests can cover these classes.

# [UI tests](https://developer.android.com/training/testing/fundamentals/what-to-test#ui)

- Screen UI tests check critical user interactions in a single screen. They perform actions such as
  clicking on buttons, typing in forms, and checking visible states. One test class per screen is a
  good starting point.
- User flow tests or Navigation tests, covering most common paths. These tests simulate a user
  moving through a navigation flow. They are simple tests, useful for checking for run-time crashes
  in initialization.
