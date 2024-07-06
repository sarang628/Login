# [Benefits of testing](https://developer.android.com/training/testing/fundamentals#benefits)

You might use different devices and emulators</br>
change the system language,</br>
and try to generate every user error or traverse every user flow.</br>

manual testing scales poorly</br>
easy to overlook regressions in your app's behavior</br>

# [Types of tests in Android](https://developer.android.com/training/testing/fundamentals#test-types)

- Functional testing: does my app do what it's supposed to?
- Performance testing: does it do it quickly and efficiently?
- Accessibility testing: does it work well with accessibility services?
- Compatibility testing: does it work well on every device and API level?

# [Scope](https://developer.android.com/training/testing/fundamentals#scope)

- Unit tests or small tests only verify a very small portion of the app, such as a method or class.
- End-to-end tests or big tests verify larger parts of the app at the same time, such as a whole
  screen or user flow.
- Medium tests are in between and check the integration between two or more units.

# [Instrumented versus local tests](https://developer.android.com/training/testing/fundamentals#instrumented-vs-local)

# [Defining a testing strategy](https://developer.android.com/training/testing/fundamentals#testing-strategy)

In an ideal world, you would test every line of code in your app on every device that your app
is<br>
compatible with. Unfortunately, this approach is too slow and costly to be practical.<br>

# [Testable architecture](https://developer.android.com/training/testing/fundamentals#architecture)

## [Approaches to decoupling](https://developer.android.com/training/testing/fundamentals#decoupling)

- Split an app into layers such as Presentation, Domain, and Data. You can also split an app into
  modules, one per feature.
- Avoid adding logic to entities that have large dependencies, such as activities and fragments. Use
  these classes as entry points to the framework and move UI and business logic elsewhere, such as
  to a Composable, ViewModel, or domain layer.
- Avoid direct framework dependencies in classes containing business logic. For example, don't use
  Android Contexts in ViewModels.
- Make dependencies easy to replace. For example, use interfaces instead of concrete
  implementations. Use Dependency injection even if you don't use a DI framework.