# Compose-State-Events

[![](https://jitpack.io/v/leonard-palm/compose-state-events.svg)](https://jitpack.io/#leonard-palm/compose-state-events)

A new way to implement One-Time-UI-Events (former SingleLiveEvent) in a Compose world.

This library will help you to avoid implementing any antipatterns regarding One-Time-UI-Events as despribed by Manuel Vivo's [article](https://medium.com/androiddevelopers/viewmodel-one-off-event-antipatterns-16a1da869b95).

See the samples below on how to effectively use `StateEvent` in your view's state and `EventEffect` in your composables.

# How to use

> Imagine a simple usecase where you need to fetch some list data from an API and display the result on the screen.



### View State Object
```kotlin
data class FlowerViewState(
    val flowers: List<Flower> = emptyList(),
    val isLoadingFlowers: Boolean = false,
    val downloadSucceededEvent: StateEvent = consumed,
    val downloadFailedEvent: StateEventWithContent<Int> = consumed()
)
```
> Imagine we would like to show a green success snackbar or a red failure snackbar after the loading has finished. 
These two events would be represented with the two new fields `downloadSucceededEvent` and `downloadFailedEvent` in our view state object.

Use the `StateEventWithContent<T>` when you need to pass some data to the consumer (the composable). 
In the example above a StringRes with a fitting error description is passed.

### ViewModel
```kotlin
private val _stateStream = MutableStateFlow(FlowerViewState())
val stateStream = _stateStream.asStateFlow()

private var state: FlowerViewState
    get() = _stateStream.value
    set(newState) {
        _stateStream.update { newState }
    }
    
fun loadFlowers(){
  viewModelScope.launch {
    state = state.copy(isLoading = true)
    state = when (val apiResult = loadAllFlowersFromApiUseCase.call()) {
        is Success -> state.copy(flowers: apiResult, downloadSucceededEvent = triggered)
        is Failure -> state.copy(downloadFailedEvent = triggered(R.string.error_load_flowers))
    }
    state = state.copy(isLoading = false)
  }
}

fun onConsumedDownloadSucceededEvent(){
  state = state.copy(downloadSucceededEvent = consumed)
}

fun onConsumedDownloadFailedEvent(){
  state = state.copy(downloadFailedEvent = consumed())
}
```
To trigger an event without any data just use the `triggered` value, otherwise use the `triggered(content: T)` function.
To consume an event without any data just use the `consumed` value, otherwise use the `consumed()` function.

### Composable

```kotlin
val viewModel: MainViewModel = viewModel()
val viewState: MainViewState by viewModel.stateStream.collectAsStateLifecycleAware()

EventEffect(event = viewState.downloadSucceededEvent, onConsumed = viewModel::onConsumedDownloadSucceededEvent) {
    scaffoldState.snackbarHostState.showSnackbar("Download succeeded.")
}

EventEffect(event = viewState.downloadFailedEvent, onConsumed = viewModel::onConsumedDownloadFailedEvent) { stringRes ->
    scaffoldState.snackbarHostState.showSnackbar(context.resources.getString(stringRes))
}
```
The `EventEffect` is a `LaunchedEffect` that will be executed, when the event is in its triggered state. 
When the event block was executed the effect calls the passed `onConsumed` callback to force you to set the view state field to be consumed.

# Installation

```gradle
allprojects {
   repositories {
       ...
       maven { url "https://jitpack.io" }
   }
}
dependencies {
   // The latest version is available in the badget at the top, replace X.X.X with that version
   implementation 'com.github.leonard-palm:compose-state-events:X.X.X'
}
``` 
