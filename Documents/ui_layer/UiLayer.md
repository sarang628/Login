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
3. Consume user input events from those assembled UI elements and reflect their effects in the UI
   data as needed.
4. Repeat steps 1 through 3 for as long as necessary.

# [Define UI state](https://developer.android.com/topic/architecture/ui-layer#define-ui-state)

What is UI state?
information that the app presents to the user

UI vs UIState?
UI is what the user sees
UI state is what the app says they should see
UI is the visual representation of the UI state

## Immutability

benefit

- guarantees regarding the state of the application at an instant in time
- This frees up the UI to focus on a single role
- you should never modify the UI state in the UI directly
- sole source of its data

# [Manage state with Unidirectional Data Flow](https://developer.android.com/topic/architecture/ui-layer#udf)

UI state is an immutable snapshot
state might change over time
user interaction or other events that modify the underlying data
a mediator to process them

## State holder

production of UI state and contain the necessary logic
state holder to produce the UI state

## Types of logic

business logic -
the implementation of product requirements for app data
Business logic is usually placed in the domain or data layers, but never in the UI layer.

UI behavior logic or UI logic
how to display state changes on the screen

## Why use UDF?

UDF models the cycle of state production
where state changes originate
where they are transformed
where they are finally consumed
This separation lets the UI do exactly what its name implies

UDF allows for the following:

- <b>Data consistency.</b> There is a single source of truth for the UI.
- <b>Testability.</b> The source of state is isolated and therefore testable independent of the UI.
- <b>Maintainability.</b> Mutation of state follows a well-defined pattern where mutations are a
  result of both user events and the sources of data they pull from.

## Expose UI state
you should expose the UI state in an observable data holder like LiveData or StateFlow
A common way of creating a stream of UiState is by exposing a backing mutable stream as an immutable stream from the ViewModel
The ViewModel can then expose methods that internally mutate the state, publishing updates for the UI to consume.